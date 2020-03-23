package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.dao.BestChoiceDAO;
import com.example.demo.dao.CategoriesDAO;
import com.example.demo.dao.LevelDAO;
import com.example.demo.dao.MatchDAO;
import com.example.demo.dao.MultipleChoiceDAO;
import com.example.demo.dao.QuestionDAO;
import com.example.demo.dao.TypeDAO;
import com.example.demo.dto.BestChoiceInfo;
import com.example.demo.dto.CreateQuestionInfo;
import com.example.demo.dto.DeleteQuestionInfo;
import com.example.demo.dto.MatchInfo;
import com.example.demo.dto.MultipleChoiceInfo;
import com.example.demo.dto.NumericalInfo;
import com.example.demo.dto.ShortAnswerInfo;
import com.example.demo.dto.True_FalseInfo;
import com.example.demo.dto.UpdateQuestionInfo;
import com.example.demo.dto.UpdateQuestionStatusInfo;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.BestChoice;
import com.example.demo.model.Category;
import com.example.demo.model.Level;
import com.example.demo.model.Match;
import com.example.demo.model.MultipleChoice;
import com.example.demo.model.Question;
import com.example.demo.model.Types;
import com.google.gson.Gson;

@Service
public class QuestionService {
	@Autowired
	QuestionDAO questionDAO;
	@Autowired 
	CategoriesDAO categoriesDAO;
	@Autowired
	LevelDAO levelDAO;
	@Autowired 
	TypeDAO typeDAO;
	@Autowired 
	MatchDAO matchDAO;
	@Autowired 
	BestChoiceDAO bestchoiceDAO;
	@Autowired
	MultipleChoiceDAO multiplechoiceDAO;

	public Question addQuestion(CreateQuestionInfo createQuestionInfo) throws ServiceException {
		Question questions = new Question();
		try {
			int typeId=createQuestionInfo.getType_Id();
			List<MatchInfo> match = null;
			int bestChoiceSize=0;
			int multipleChoiceSize=0;
			Gson g = new Gson();
			int size = 0;
			String opt=null;
			List<BestChoiceInfo> bestChoice=null;
			List<MultipleChoiceInfo> multipleChoice=null;
			switch(typeId) {
			case 1:
				 bestChoice=createQuestionInfo.getBest_choice();
				bestChoiceSize=bestChoice.size();
			case 2:
				True_FalseInfo true_false = createQuestionInfo.getTrue_false();
				
				opt = g.toJson(true_false, True_FalseInfo.class);
				break;
			case 4:
				ShortAnswerInfo shortAnswer=createQuestionInfo.getShortAnswer();
				opt = g.toJson(shortAnswer, ShortAnswerInfo.class);
				
				break;
			case 5:
				 match = createQuestionInfo.getMatch();
				size = match.size();
				//Match matchobj=new Match();
				
				break;
			case 3:
				 multipleChoice=createQuestionInfo.getMultiple_choice();
				 multipleChoiceSize=multipleChoice.size();
			case 6:
				NumericalInfo numerical = createQuestionInfo.getNumericalValue();
				opt = g.toJson(numerical, NumericalInfo.class);
				break;
			
			}
			questions.setTitle(createQuestionInfo.getTitle());
			Category category = new Category();
			category.setId(createQuestionInfo.getCategory_Id());
			questions.setCategory_Id(category);
			questions.setContent(createQuestionInfo.getContent());
			questions.setScore(createQuestionInfo.getScore());
			questions.setStatus(createQuestionInfo.getStatus());
			Level level = new Level();
			level.setId(createQuestionInfo.getLevel_Id());
			questions.setLevel_Id(level);
			questions.setTags(createQuestionInfo.getTags());
			questions.setSkill_points(createQuestionInfo.getSkill_points());
			Types type = new Types();
			type.setId(createQuestionInfo.getType_Id());
			questions.setType_Id(type);
			
			// OptionInfo option= new OptionInfo();
			// String opt=createQuestionInfo.getOption().toString();
			//questions.setOption(opt);
//	  questions.setAnswer(createQuestionInfo.getOption());
//	  option.setcExplanation(cExplanation);
//	  questions.setOption(createQuestionInfo.getOption());
			questions.setDuration(createQuestionInfo.getDuration());
			questions.setOption(opt);
			
			questionDAO.save(questions);
			int id=questionDAO.getId();
			//int matchId;
			//System.out.print(id);
			//int matchIdArray[]=new int[bestChoiceSize];
			//int correctAnswer=0;
			
			if(typeId==1) {
				//BestChoice bestChoiceobj = new BestChoice();
				for(int i=0;i<bestChoiceSize;i++) {
					//System.out.print(bestChoice.get(i).getIsSticky());
					//System.out.print(bestChoice.get(i).getIsYes());
					bestchoiceDAO.insert(bestChoice.get(i).getValue(),bestChoice.get(i).getIsSticky(),bestChoice.get(i).getIsYes(),id);
					
					
					
				}				
			}
			if(typeId==3) {
				//BestChoice bestChoiceobj = new BestChoice();
				for(int i=0;i<multipleChoiceSize;i++) {
					//System.out.print(bestChoice.get(i).getIsSticky());
					//System.out.print(bestChoice.get(i).getIsYes());
					multiplechoiceDAO.insert(multipleChoice.get(i).getValue(),multipleChoice.get(i).getIsSticky(),multipleChoice.get(i).getIsYes(),id);
					
					
					
				}				
			}
			
			//System.out.print(Arrays.toString(matchIdArray));
			
			if(typeId==5) {
				Match matchobj=new Match();
			for (int i=0;i<size;i++) {
			matchDAO.insert(id,match.get(i).getCol_a(),match.get(i).getCol_b(),match.get(i).getMatch_option_id());
			}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("Unable to add new question");
		}
		return questions;
		
	}
	public List<Level> getLevels()throws ServiceException{
		List<Level> levels;
		levels = levelDAO.findAll();
		if (levels == null) {
			throw new ServiceException("Unable to fetch levels");
		}
		return levels;
	}
	public List<Types> getTypes()throws ServiceException{
		List<Types> types;
		types = typeDAO.findAll();
		if (types == null) {
			throw new ServiceException("Unable to fetch levels");
		}
		return types;
	}
	public Page<Question> getactivatedQuestions(Integer pageNo, Integer pageSize, String sortBy) throws ServiceException {
		Page<Question> activatedQuestions;
		String status = "active";
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		activatedQuestions = (Page<Question>) questionDAO.getActivatedQuestions(status,paging);
		if (activatedQuestions == null) {
			throw new ServiceException("Unable to fetch activated question");
		}
		return activatedQuestions;
	}

	public Page<Question> getDeactivatedQuestions(Integer pageNo, Integer pageSize, String sortBy) throws ServiceException {
		Page<Question> deactivatedQuestions;
		String status = "deactive";
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		deactivatedQuestions = questionDAO.getDeactivatedQuestions(status,paging);
		if (deactivatedQuestions == null) {
			throw new ServiceException("Unable to fetch deactivated question");
		}
		return deactivatedQuestions;
	}
	public Page<Question> getQuestionBasedOnTags(String tagName,String status,Integer pageNo, Integer pageSize, String sortBy) throws ServiceException {
	Page<Question> questions;
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		questions =  questionDAO.getQuestionBasedOnTags(tagName,status,paging);
		if (questions == null) {
			throw new ServiceException("Question not found for a given tag");
		}
		return questions;
	}
	public List<Category> getCategories()throws ServiceException{
		List<Category> categories;
		categories = categoriesDAO.findAll();
		if (categories == null) {
			throw new ServiceException("Unable to fetch categories");
		}
		return categories;
	}
	

	public Optional<Question> getQuestion(int id) throws ServiceException {
		Optional<Question> question;

		if (questionDAO.existsById(id)) {
			question = questionDAO.findById(id);
		} else {
			throw new ServiceException("Unable to fetch question");
		}
		return question;
	}

	

	public String updateQuestion(UpdateQuestionInfo updateQuestionInfo) throws ServiceException {
		Question questions = new Question();
		List<MatchInfo> match = null;
		List<BestChoiceInfo> bestChoice=null;
		try {
			if (questionDAO.existsById(updateQuestionInfo.getId())) {
				int typeId=updateQuestionInfo.getType_Id();
				
				Gson g = new Gson();
				int size = 0;
				int bestChoiceSize=0;
				int multipleChoiceSize=0;
				String opt=null;
				List<MultipleChoiceInfo> multipleChoice=null;
				switch(typeId) {
				case 1:
					 bestChoice=updateQuestionInfo.getBest_choice();
					bestChoiceSize=bestChoice.size();
				case 2:
					True_FalseInfo true_false = updateQuestionInfo.getTrue_false();
					
					opt = g.toJson(true_false, True_FalseInfo.class);
					break;
				case 3:
					 multipleChoice=updateQuestionInfo.getMultiple_choice();
					 multipleChoiceSize=multipleChoice.size();
				case 4:
					ShortAnswerInfo shortAnswer=updateQuestionInfo.getShortAnswer();
					opt = g.toJson(shortAnswer, ShortAnswerInfo.class);
					break;
				
				case 6:
					NumericalInfo numerical = updateQuestionInfo.getNumericalValue();
					opt = g.toJson(numerical, NumericalInfo.class);
					break;
				case 5:
					 match = updateQuestionInfo.getMatch();
					size = match.size();
					//Match matchobj=new Match();
					
					break;
				}
				questions.setId(updateQuestionInfo.getId());
				questions.setTitle(updateQuestionInfo.getTitle());
				Category category = new Category();
				category.setId(updateQuestionInfo.getCategory_Id());
				questions.setCategory_Id(category);
				questions.setContent(updateQuestionInfo.getContent());
				questions.setScore(updateQuestionInfo.getScore());
				Level level = new Level();
				level.setId(updateQuestionInfo.getLevel_Id());
				questions.setLevel_Id(level);
				questions.setSkill_points(updateQuestionInfo.getSkill_points());
				Types type = new Types();
				type.setId(updateQuestionInfo.getType_Id());
				questions.setType_Id(type);
				questions.setOption(opt);
				questions.setStatus(updateQuestionInfo.getStatus());
				questions.setTags(updateQuestionInfo.getTags());
				//questions.setOption(updateQuestionInfo.getOption());
				questions.setDuration(updateQuestionInfo.getDuration());
				questionDAO.save(questions);
				//int id=questionDAO.getId();
				//System.out.print(id);
				if(typeId==1) {
					ArrayList<Integer> idArray=new ArrayList<>();
					//int idArray[]=new int[bestChoiceSize];
					
					BestChoice bestChoiceobj = new BestChoice();
					List<BestChoice> bc=new ArrayList<>();
					bc=bestchoiceDAO.getBestChoice(updateQuestionInfo.getId());
					ArrayList<Integer> dbIds=new ArrayList<>();
					//System.out.print(bc.size());
					for(int i=0;i<bc.size();i++) {
						dbIds.add(bc.get(i).getId());
						//System.out.print(dbIds[i]);
					}
					
					for(int i=0;i<bestChoiceSize;i++) {
						
						idArray.add(bestChoice.get(i).getId());
						if(idArray.get(i)!=0) {
						//System.out.print(idArray.get(i));
						bestchoiceDAO.update(bestChoice.get(i).getValue(),bestChoice.get(i).getIsSticky(),bestChoice.get(i).getIsYes(),updateQuestionInfo.getId(),bestChoice.get(i).getId());
						}
						else {
						bestchoiceDAO.insert(bestChoice.get(i).getValue(),bestChoice.get(i).getIsSticky(),bestChoice.get(i).getIsYes(),updateQuestionInfo.getId());
						}
						}
					dbIds.removeAll(idArray);
				
					for(int i=0;i<dbIds.size();i++) {
						bestchoiceDAO.deleteById(dbIds.get(i));
					}
					
				}
				if(typeId==5) {
					ArrayList<Integer> idArray=new ArrayList<>();
					ArrayList<Integer> dbId=new ArrayList<>();
					Match matchobj=new Match();
					List<Match> matchData=new ArrayList<>();
					matchData=matchDAO.match(updateQuestionInfo.getId());
					for(int i=0;i<matchData.size();i++) {
						dbId.add(matchData.get(i).getId());
						//System.out.print(dbIds.get(i));
					}
				for (int i=0;i<size;i++) {
					idArray.add(match.get(i).getMid());
					if(idArray.get(i)!=0) {
						matchDAO.update(match.get(i).getCol_a(),match.get(i).getCol_b(),match.get(i).getMatch_option_id(),updateQuestionInfo.getId(),match.get(i).getMid());
					}
					else {
						matchDAO.insert(updateQuestionInfo.getId(), match.get(i).getCol_a(),match.get(i).getCol_b(), match.get(i).getMatch_option_id());
					}
					}
				dbId.removeAll(idArray);
				
				for(int i=0;i<dbId.size();i++) {
					//System.out.print(dbId.get(i));
					matchDAO.delete(dbId.get(i));
				}
					
				}
				if(typeId==3) {
					ArrayList<Integer> idArray=new ArrayList<>();
					//int idArray[]=new int[bestChoiceSize];
					
					MultipleChoice multipleChoiceobj = new MultipleChoice();
					List<MultipleChoice> mc=new ArrayList<>();
					mc=multiplechoiceDAO.getMultipleChoice(updateQuestionInfo.getId());
					ArrayList<Integer> dbIds=new ArrayList<>();
					//System.out.print(bc.size());
					for(int i=0;i<mc.size();i++) {
						dbIds.add(mc.get(i).getId());
						//System.out.print(dbIds[i]);
					}
					
					for(int i=0;i<multipleChoiceSize;i++) {
						
						idArray.add(multipleChoice.get(i).getId());
						if(idArray.get(i)!=0) {
						//System.out.print(idArray.get(i));
						multiplechoiceDAO.update(multipleChoice.get(i).getValue(),multipleChoice.get(i).getIsSticky(),multipleChoice.get(i).getIsYes(),updateQuestionInfo.getId(),multipleChoice.get(i).getId());
						}
						else {
							multiplechoiceDAO.insert(multipleChoice.get(i).getValue(),multipleChoice.get(i).getIsSticky(),multipleChoice.get(i).getIsYes(),updateQuestionInfo.getId());
						}
						}
					dbIds.removeAll(idArray);
				
					for(int i=0;i<dbIds.size();i++) {
						multiplechoiceDAO.deleteById(dbIds.get(i));
					}
					
				}
				return "Updated Question Details Successfully";
			} else {
				return "Question does not exist";
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("Unable to update question");
		}

	}
	public String updateQuestionStatus(UpdateQuestionStatusInfo updateQuestionStatusInfo) throws ServiceException {
		try {
			List<Integer> array_of_ids = (updateQuestionStatusInfo.getId());
			int length = array_of_ids.size();
			for (int i = 0; i < length; i++) {
				if (questionDAO.existsById(array_of_ids.get(i))) {
					continue;
				} else {
					return "Id does not exist";
				}
			}
			String status = updateQuestionStatusInfo.getStatus();
			questionDAO.updateStatus(status, array_of_ids);
			return "Updated Status";

		}

		catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("Unable to update Status");
		}
	}

	public String deleteQuestion(DeleteQuestionInfo deleteQuestionInfo) throws ServiceException {
		try {
			List<Integer> array_of_ids = (deleteQuestionInfo.getId());
			int length = array_of_ids.size();
			for (int i = 0; i < length; i++) {
				if (questionDAO.existsById(array_of_ids.get(i))) {
					questionDAO.deleteById(array_of_ids.get(i));
				}

				else {
					return "Id does not exist";
				}
			}
			return "Question Deleted";

		}

		catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("Unable to delete question");
		}
	}
	public List<Match> getMatches(int qid)throws ServiceException {
		List<Match> match;
		match=matchDAO.match(qid);
		if (match == null) {
			throw new ServiceException("Unable to fetch matches");
		}
		return match;
	}
	public List<BestChoice> getBestChoice(int qid) throws ServiceException{
		List<BestChoice> bestChoice;
		bestChoice=bestchoiceDAO.getBestChoice(qid);
		if(bestChoice==null){
			throw new ServiceException("Unable to fetch Best Choices");
		}
		return bestChoice;
	}
	
	public List<MultipleChoice> getMultipleChoice(int qid) throws ServiceException{
		List<MultipleChoice> multipleChoice;
		multipleChoice=multiplechoiceDAO.getMultipleChoice(qid);
		if(multipleChoice==null){
			throw new ServiceException("Unable to fetch multiple Choices");
		}
		return multipleChoice;
	}
}