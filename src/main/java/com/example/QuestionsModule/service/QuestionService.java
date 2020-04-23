package com.example.QuestionsModule.service;

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

import com.example.QuestionsModule.dao.BestChoiceDAO;
import com.example.QuestionsModule.dao.CategoriesDAO;
import com.example.QuestionsModule.dao.LevelDAO;
import com.example.QuestionsModule.dao.MatchDAO;
import com.example.QuestionsModule.dao.MultipleChoiceDAO;
import com.example.QuestionsModule.dao.QuestionDAO;
import com.example.QuestionsModule.dao.TypeDAO;
import com.example.QuestionsModule.dto.BestChoiceInfo;
import com.example.QuestionsModule.dto.CreateQuestionInfo;
import com.example.QuestionsModule.dto.DeleteQuestionInfo;
import com.example.QuestionsModule.dto.MatchInfo;
import com.example.QuestionsModule.dto.MultipleChoiceInfo;
import com.example.QuestionsModule.dto.NumericalInfo;
import com.example.QuestionsModule.dto.ShortAnswerInfo;
import com.example.QuestionsModule.dto.True_FalseInfo;
import com.example.QuestionsModule.dto.UpdateQuestionInfo;
import com.example.QuestionsModule.dto.UpdateQuestionStatusInfo;
import com.example.QuestionsModule.exception.DBException;
import com.example.QuestionsModule.exception.ServiceException;
import com.example.QuestionsModule.model.BestChoice;
import com.example.QuestionsModule.model.Category;
import com.example.QuestionsModule.model.Level;
import com.example.QuestionsModule.model.Match;
import com.example.QuestionsModule.model.MultipleChoice;
import com.example.QuestionsModule.model.Question;
import com.example.QuestionsModule.model.Types;
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

	public void addQuestion(CreateQuestionInfo createQuestionInfo) throws ServiceException {
		Question questions = new Question();
		try {
			int typeId = createQuestionInfo.getType_Id();
			List<MatchInfo> match = null;
			int bestChoiceSize = 0;
			int multipleChoiceSize = 0;
			Gson g = new Gson();
			int size = 0;
			String opt = null;
			List<BestChoiceInfo> bestChoice = null;
			List<MultipleChoiceInfo> multipleChoice = null;
			switch (typeId) {
			case 1:
				bestChoice = createQuestionInfo.getBest_choice();
				bestChoiceSize = bestChoice.size();
			case 2:
				True_FalseInfo true_false = createQuestionInfo.getTrue_false();

				opt = g.toJson(true_false, True_FalseInfo.class);
				break;
			case 4:
				ShortAnswerInfo shortAnswer = createQuestionInfo.getShortAnswer();
				opt = g.toJson(shortAnswer, ShortAnswerInfo.class);

				break;
			case 5:
				match = createQuestionInfo.getMatch();
				size = match.size();

				break;
			case 3:
				multipleChoice = createQuestionInfo.getMultiple_choice();
				multipleChoiceSize = multipleChoice.size();
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
			questions.setDuration(createQuestionInfo.getDuration());
			questions.setOption(opt);

			questionDAO.save(questions);
			int id = questionDAO.getId();

			if (typeId == 1) {
				for (int i = 0; i < bestChoiceSize; i++) {
					bestchoiceDAO.insert(bestChoice.get(i).getValue(), bestChoice.get(i).getIsSticky(),
							bestChoice.get(i).getIsYes(), id);

				}
			}
			if (typeId == 3) {
				for (int i = 0; i < multipleChoiceSize; i++) {
					multiplechoiceDAO.insert(multipleChoice.get(i).getValue(), multipleChoice.get(i).getIsSticky(),
							multipleChoice.get(i).getIsYes(), id);

				}
			}


			if (typeId == 5) {
				Match matchobj = new Match();
				for (int i = 0; i < size; i++) {
					matchDAO.insert(id, match.get(i).getCol_a(), match.get(i).getCol_b(),
							match.get(i).getMatch_option_id());
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("Unable to add new question");
		}
		

	}

	public List<Level> getLevels() throws ServiceException {
		List<Level> levels;
		levels = levelDAO.findAll();
		if (levels == null) {
			throw new ServiceException("Unable to fetch levels");
		}
		return levels;
	}

	public List<Types> getTypes() throws ServiceException {
		List<Types> types;
		types = typeDAO.findAll();
		if (types == null) {
			throw new ServiceException("Unable to fetch levels");
		}
		return types;
	}

	public Page<Question> getactivatedQuestions(Integer pageNo, Integer pageSize, String sortBy)
			throws ServiceException {
		Page<Question> activatedQuestions;
		String status = "active";
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		try {
			activatedQuestions = (Page<Question>) questionDAO.getActivatedQuestions(status, paging);
			return activatedQuestions;

		} catch (DBException e) {
			throw new ServiceException("Unable to fetch activated question");

		}
	}

	public Page<Question> getDeactivatedQuestions(Integer pageNo, Integer pageSize, String sortBy)
			throws ServiceException {
		Page<Question> deactivatedQuestions;
		String status = "deactive";
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		try {
			deactivatedQuestions = questionDAO.getDeactivatedQuestions(status, paging);
			return deactivatedQuestions;

		} catch (DBException e) {
			throw new ServiceException("Unable to fetch deactivated question");
		}
	}

	public Page<Question> getQuestionBasedOnTags(String tagName, String status, Integer pageNo, Integer pageSize,
			String sortBy) throws ServiceException {
		Page<Question> questions;
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		try {
			questions = questionDAO.getQuestionBasedOnTags(tagName, status, paging);
			return questions;
		} catch (DBException e) {
			throw new ServiceException("Question not found for a given tag");

		}
	}

	public List<Category> getCategories() throws ServiceException {
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

	public void updateQuestion(UpdateQuestionInfo updateQuestionInfo) throws ServiceException {
		Question questions = new Question();
		List<MatchInfo> match = null;
		List<BestChoiceInfo> bestChoice = null;
		try {
			if (questionDAO.existsById(updateQuestionInfo.getId())) {
				int typeId = updateQuestionInfo.getType_Id();

				Gson g = new Gson();
				int size = 0;
				int bestChoiceSize = 0;
				int multipleChoiceSize = 0;
				String opt = null;
				List<MultipleChoiceInfo> multipleChoice = null;
				switch (typeId) {
				case 1:
					bestChoice = updateQuestionInfo.getBest_choice();
					bestChoiceSize = bestChoice.size();
				case 2:
					True_FalseInfo true_false = updateQuestionInfo.getTrue_false();

					opt = g.toJson(true_false, True_FalseInfo.class);
					break;
				case 3:
					multipleChoice = updateQuestionInfo.getMultiple_choice();
					multipleChoiceSize = multipleChoice.size();
				case 4:
					ShortAnswerInfo shortAnswer = updateQuestionInfo.getShortAnswer();
					opt = g.toJson(shortAnswer, ShortAnswerInfo.class);
					break;

				case 6:
					NumericalInfo numerical = updateQuestionInfo.getNumericalValue();
					opt = g.toJson(numerical, NumericalInfo.class);
					break;
				case 5:
					match = updateQuestionInfo.getMatch();
					size = match.size();

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
				questions.setDuration(updateQuestionInfo.getDuration());
				questionDAO.save(questions);
				if (typeId == 1) {
					ArrayList<Integer> idArray = new ArrayList<>();

					BestChoice bestChoiceobj = new BestChoice();
					List<BestChoice> bc = new ArrayList<>();
					bc = bestchoiceDAO.getBestChoice(updateQuestionInfo.getId());
					ArrayList<Integer> dbIds = new ArrayList<>();
					for (int i = 0; i < bc.size(); i++) {
						dbIds.add(bc.get(i).getId());
					}

					for (int i = 0; i < bestChoiceSize; i++) {

						idArray.add(bestChoice.get(i).getId());
						if (idArray.get(i) != 0) {
							// System.out.print(idArray.get(i));
							bestchoiceDAO.update(bestChoice.get(i).getValue(), bestChoice.get(i).getIsSticky(),
									bestChoice.get(i).getIsYes(), updateQuestionInfo.getId(),
									bestChoice.get(i).getId());
						} else {
							bestchoiceDAO.insert(bestChoice.get(i).getValue(), bestChoice.get(i).getIsSticky(),
									bestChoice.get(i).getIsYes(), updateQuestionInfo.getId());
						}
					}
					dbIds.removeAll(idArray);

					for (int i = 0; i < dbIds.size(); i++) {
						bestchoiceDAO.deleteById(dbIds.get(i));
					}

				}
				if (typeId == 5) {
					ArrayList<Integer> idArray = new ArrayList<>();
					ArrayList<Integer> dbId = new ArrayList<>();
					Match matchobj = new Match();
					List<Match> matchData = new ArrayList<>();
					matchData = matchDAO.match(updateQuestionInfo.getId());
					for (int i = 0; i < matchData.size(); i++) {
						dbId.add(matchData.get(i).getId());
					}
					for (int i = 0; i < size; i++) {
						idArray.add(match.get(i).getMid());
						if (idArray.get(i) != 0) {
							matchDAO.update(match.get(i).getCol_a(), match.get(i).getCol_b(),
									match.get(i).getMatch_option_id(), updateQuestionInfo.getId(),
									match.get(i).getMid());
						} else {
							matchDAO.insert(updateQuestionInfo.getId(), match.get(i).getCol_a(),
									match.get(i).getCol_b(), match.get(i).getMatch_option_id());
						}
					}
					dbId.removeAll(idArray);

					for (int i = 0; i < dbId.size(); i++) {
						matchDAO.delete(dbId.get(i));
					}

				}
				if (typeId == 3) {
					ArrayList<Integer> idArray = new ArrayList<>();

					MultipleChoice multipleChoiceobj = new MultipleChoice();
					List<MultipleChoice> mc = new ArrayList<>();
					mc = multiplechoiceDAO.getMultipleChoice(updateQuestionInfo.getId());
					ArrayList<Integer> dbIds = new ArrayList<>();
					for (int i = 0; i < mc.size(); i++) {
						dbIds.add(mc.get(i).getId());
					}

					for (int i = 0; i < multipleChoiceSize; i++) {

						idArray.add(multipleChoice.get(i).getId());
						if (idArray.get(i) != 0) {
							multiplechoiceDAO.update(multipleChoice.get(i).getValue(),
									multipleChoice.get(i).getIsSticky(), multipleChoice.get(i).getIsYes(),
									updateQuestionInfo.getId(), multipleChoice.get(i).getId());
						} else {
							multiplechoiceDAO.insert(multipleChoice.get(i).getValue(),
									multipleChoice.get(i).getIsSticky(), multipleChoice.get(i).getIsYes(),
									updateQuestionInfo.getId());
						}
					}
					dbIds.removeAll(idArray);

					for (int i = 0; i < dbIds.size(); i++) {
						multiplechoiceDAO.deleteById(dbIds.get(i));
					}

				}
			} else {
				throw new ServiceException("Question does not exist");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("Unable to update question");
		}

	}

	public void updateQuestionStatus(UpdateQuestionStatusInfo updateQuestionStatusInfo) throws ServiceException {
		try {
			List<Integer> array_of_ids = (updateQuestionStatusInfo.getId());
			int length = array_of_ids.size();
			for (int i = 0; i < length; i++) {
				if (questionDAO.existsById(array_of_ids.get(i))) {
					continue;
				} else {
					throw new ServiceException("Id does not exist");
				}
			}
			String status = updateQuestionStatusInfo.getStatus();
			questionDAO.updateStatus(status, array_of_ids);
		}

		catch (Exception e) {
			throw new ServiceException("Unable to update Status");
		}
	}

	public void deleteQuestion(DeleteQuestionInfo deleteQuestionInfo) throws ServiceException {
		try {
			List<Integer> array_of_ids = (deleteQuestionInfo.getId());
			int length = array_of_ids.size();
			for (int i = 0; i < length; i++) {
				if (questionDAO.existsById(array_of_ids.get(i))) {
					questionDAO.deleteById(array_of_ids.get(i));
				}

				else {
					throw new ServiceException("Id does not exist");

				}
			}

		}

		catch (Exception e) {
			throw new ServiceException("Unable to delete question");
		}
	}

	public List<Match> getMatches(int qid) throws ServiceException {
		List<Match> match;
		try {
			match = matchDAO.match(qid);
			return match;

		} catch (DBException e) {
			throw new ServiceException("Unable to fetch matches");

		}
	}

	public List<BestChoice> getBestChoice(int qid) throws ServiceException {
		List<BestChoice> bestChoice;
		try {
			bestChoice = bestchoiceDAO.getBestChoice(qid);
			return bestChoice;

		} catch (DBException e) {
			throw new ServiceException("Unable to fetch Best Choices");

		}

	}

	public List<MultipleChoice> getMultipleChoice(int qid) throws ServiceException {
		List<MultipleChoice> multipleChoice;
		try {
			multipleChoice = multiplechoiceDAO.getMultipleChoice(qid);
			return multipleChoice;
		} catch (DBException e) {
			throw new ServiceException("Unable to fetch multiple Choices");

		}

	}
}