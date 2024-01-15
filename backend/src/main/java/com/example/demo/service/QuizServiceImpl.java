package com.example.demo.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.demo.Exception.QuestionsLengthException;
import com.example.demo.Exception.QuizAnswered;
import com.example.demo.Exception.QuizNotFoundException;
import com.example.demo.dao.QuizRepository;
import com.example.demo.dao.ResultRepository;
import com.example.demo.entity.QuestionWrapper;
import com.example.demo.entity.Questions;
import com.example.demo.entity.Quiz;
import com.example.demo.entity.Response;
import com.example.demo.entity.Result;

/**
* Implementation of the QuizService interface providing operations related to quizzes.
*/
@Service
public class QuizServiceImpl implements QuizService {

	/**
     * Repository for accessing quiz data in the database.
     */
	@Autowired
	private QuizRepository quizRepository;
	
	/**
     * Repository for accessing Result data in the database.
     */
	@Autowired
	private ResultRepository resultRepository;
	
	private ScheduledExecutorService executorService;
	

    private static Authentication authentication;
    private static String username;
   
    public void Authenticate() {
        QuizServiceImpl.authentication = SecurityContextHolder.getContext().getAuthentication();
        QuizServiceImpl.username = authentication.getName();
    }

	/**
     * Retrieves a quiz by its unique ID.
     *
     * @param id The unique ID of the quiz.
     * @return The Quiz object corresponding to the provided ID, or null if not found.
     */
	//Retrieves quiz by the unique Id
	public Quiz getQuizById(int id) throws Exception {

		Optional<Quiz> quiz1 = quizRepository.findById(id);
	
		if (quiz1.isPresent()) {
			return quiz1.get();
		}else 
			throw new QuizNotFoundException();
		
	}
	
	/**
     * Retrieves a list of all available quizzes.
     *
     * @return List of Quiz objects representing all quizzes.
     */
	public List<Quiz> getQuizzes() {
		return quizRepository.findAll();
	}
	
	/**
     * Retrieves a list of quizzes created by a specific trainer based on their username.
     *
     * @param username The username of the trainer.
     * @return List of Quiz objects created by the specified trainer.
	 * @throws QuizNotFoundException 
     */
	public List<Quiz> getQuizByUsername() throws QuizNotFoundException{
		Authenticate();
         List<Quiz> allQuiz= quizRepository.findByUsername(username) ;
         if(allQuiz.isEmpty()) {
        	 throw new QuizNotFoundException();
         }
		return allQuiz;
	}
	
	/**
     * Retrieves a list of quizzes based on the specified quiz topic.
     *
     * @param quizTopic The topic of the quizzes to retrieve.
     * @return List of Quiz objects matching the specified topic.
	 * @throws QuizNotFoundException 
     */
    public List<Map<String, Object>> getQuizByquizTopic(String quizTopic) throws QuizNotFoundException {
        List<Quiz> allQuiz = quizRepository.findByquizTopic(quizTopic);
        if (allQuiz.isEmpty()) {
            throw new QuizNotFoundException();
        }
 
        List<Map<String, Object>> newQuiz = new ArrayList<>();
        for (Quiz quiz : allQuiz) {
            Map<String, Object> quizInfoForTrainer = new HashMap<>();
 
            quizInfoForTrainer.put("quizId", quiz.getQuizId());
            quizInfoForTrainer.put("quizName", quiz.getQuizName());
            quizInfoForTrainer.put("quizTopic", quiz.getQuizTopic());
            quizInfoForTrainer.put("No.Of.Questions", quiz.getTotal_number_of_questions());
            quizInfoForTrainer.put("Duration", quiz.getTotal_duaration_of_quiz());
            quizInfoForTrainer.put("quizStatus", quiz.getQuiz_status());
 
            newQuiz.add(quizInfoForTrainer);
        }
        return newQuiz;
    }
	
	/**
     * Adds a new quiz to the database after validating its questions and duration.
     *
     * @param q The Quiz object to add.
     * @return The added Quiz object.
     * @throws QuestionsLengthException If the quiz does not contain the required number of questions.
     */
	public Quiz addQuiz(Quiz q) throws Exception{
		List<Questions> questions = q.getListOfquestions();
		boolean flag = true;
		if(questions.size()!=20) flag=false;
		
			if(flag) {
				int duration_per_question =q.getDuration_per_question();
				int total_no_of_question=q.getTotal_number_of_questions();
				int quiz_duration=total_no_of_question *duration_per_question;
				q.setTotal_duaration_of_quiz(quiz_duration);
				Quiz newQuiz = quizRepository.save(q);
				return newQuiz;
			
		}else {
			throw new QuestionsLengthException();
		}	
	}

	
//	
//	/**
//     * Retrieves a list of quizzes specifically for trainers, filtering sensitive information.
//     *
//     * @return List of Quiz objects tailored for trainers.
//     */
//	@Override
//	public List<Map<String, Object>> getQuizzesForTrainer() {
//		 List<Quiz> allQuiz=quizRepository.findAll();
//		 List<Map<String, Object>> newQuiz=new ArrayList<>(); 
//		 for(Quiz quiz:allQuiz) {
//			 Map<String, Object> quizInfoForTrainer=new HashMap<>();
//				
//			 quizInfoForTrainer.put("quizId", quiz.getQuizId());
//			 quizInfoForTrainer.put("quizName", quiz.getQuizName());
//			 quizInfoForTrainer.put("quizTopic", quiz.getQuizTopic());
//			 quizInfoForTrainer.put("No.Of.Questions", quiz.getTotal_number_of_questions());
//			 quizInfoForTrainer.put("Duration", quiz.getTotal_duaration_of_quiz());
//			 quizInfoForTrainer.put("quizStatus", quiz.getQuiz_status());
//			 
//			 newQuiz.add(quizInfoForTrainer);
//			 }
//		return newQuiz;
//	}
//	
	
	/**
     * Updates the status of a quiz based on its ID and the specified status.
     *
     * @param id     The ID of the quiz to be updated.
     * @param status The new status for the quiz ("Active" or "Inactive").
     * @return The updated Quiz object with the new status.
	 * @throws QuizNotFoundException 
     */
	@Override
	public Quiz updateStatusOfQuiz(int id, String status) throws QuizNotFoundException {
		Optional<Quiz> oldquiz=quizRepository.findById(id);
		Quiz newQuiz=new Quiz();
		if(oldquiz.isPresent()) {
			Quiz oldQuizData=oldquiz.get();
			newQuiz.setQuizId(oldQuizData.getQuizId());
			newQuiz.setQuizName(oldQuizData.getQuizName());
			newQuiz.setQuizTopic(oldQuizData.getQuizTopic());
			newQuiz.setQuiz_status(status);
			newQuiz.setDuration_per_question(oldQuizData.getDuration_per_question());
			newQuiz.setTotal_duaration_of_quiz(oldQuizData.getTotal_duaration_of_quiz());
			newQuiz.setListOfquestions(oldQuizData.getListOfquestions());
			newQuiz.setUsername(oldQuizData.getUsername());
			
			quizRepository.save(newQuiz);		
		}else{
			throw new QuizNotFoundException();	
		}
		return newQuiz;
	}
	
	@Override
	public ResponseEntity<List<QuestionWrapper>> takeQuiz(Integer id,String username) throws QuizAnswered{
		//check if previously attempted
		int answered = resultRepository.hasAnswered(id, username);
		if (answered >= 3) throw new QuizAnswered();
		Optional<Quiz> quizOptional = Optional.ofNullable(quizRepository.getQuizForStudent(id));
        
        if (quizOptional.isPresent()) {
            Quiz quiz = quizOptional.get();
            AtomicInteger remainingTime = new AtomicInteger(quiz.getTotal_duaration_of_quiz() * 60); // Convert minutes to seconds
            
            startQuizTimer(remainingTime);
            
            List<Questions> questionFromDbList = quiz.getListOfquestions();
            List<QuestionWrapper> questionForStudent = new ArrayList<>();
            
            for (Questions que : questionFromDbList) {
                QuestionWrapper qWrapper = new QuestionWrapper(
                        que.getQuestionId(), que.getQuestionStatement(), que.getOption1(),
                        que.getOption2(), que.getOption3(), que.getOption4()
                );
                questionForStudent.add(qWrapper);
            }
            return new ResponseEntity<>(questionForStudent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
	}

	@Override
	public ResponseEntity<String> calculateResult(Integer id, Result result) {
		Authenticate();
		result.setUsername(username);
		result.setQuizId(id);
		Quiz quiz = quizRepository.findById(id).orElse(null);

		if (quiz == null) {
			return new ResponseEntity<>("Quiz not found", HttpStatus.NOT_FOUND);
		}

		List<Questions> questions = quiz.getListOfquestions();
		int totalQuestions = questions.size();
		int correctAnswers = 0;

		List<Response> responses = result.getListOfResponses();

		for (int i = 0; i < responses.size(); i++) {
			Response response = responses.get(i);
			Questions question = questions.get(i);
			if (response.getResponse().equals(question.getCorrectOption())) {
				correctAnswers++;
			}
		}

		double percentage = (double) correctAnswers / totalQuestions * 100;
		String resultMessage = "Correct answers: " + correctAnswers;

		if (percentage >= 70) {
			resultMessage += "\nCongratulations! You have passed with " + percentage + "% correct answers.";
		} else {
			resultMessage += "\nSorry, you have not passed. You scored " + percentage + "% correct answers.";
		}

		result.setPercentage(percentage);
		result.setScore(correctAnswers);

		resultRepository.save(result);

		return new ResponseEntity<>(resultMessage, HttpStatus.OK);
	}

	@Override
	public Set<String> getAllTopics() {
		return quizRepository.getAllTopics();
    }

	@Override
	public List<LinkedHashMap<String, Object>> getQuizByquizTopics(String quizTopic) {
		 List<Quiz> allQuiz= quizRepository.findByquizTopic(quizTopic) ;
		 List<LinkedHashMap<String, Object>> newQuiz = new ArrayList<>();
		 for(Quiz quiz:allQuiz) {
			 LinkedHashMap<String, Object> simpDataObjects = new LinkedHashMap<>();
			 
			 simpDataObjects.put("quizId", quiz.getQuizId());
			 simpDataObjects.put("quizTopic", quiz.getQuizTopic());
			 simpDataObjects.put("quizName", quiz.getQuizName());
			 simpDataObjects.put("No.Of.Questions", quiz.getTotal_number_of_questions());
			 simpDataObjects.put("Duration", quiz.getTotal_duaration_of_quiz());
			 simpDataObjects.put("Status", quiz.getQuiz_status());
			 
			 newQuiz.add(simpDataObjects);
			 }
		return newQuiz;
	}

	@Override
	public List<LinkedHashMap<String, Object>> showLeaderBoard(int id) {
		List<Result> listOfToppers= resultRepository.showLeaderBoard(id);
		return simplifyResponse(listOfToppers);
	}
	
	public List<LinkedHashMap<String, Object>>simplifyResponse(List<Result> rs){
		List<LinkedHashMap<String, Object>> simplifiedObj = new ArrayList<>();
		int count=1;
		for(Result dataObjects : rs) {
			Quiz quiz=quizRepository.findById(dataObjects.getQuizId()).orElse(null) ;
			LinkedHashMap<String, Object> simpDataObjects = new LinkedHashMap<>();
			simpDataObjects.put("Rank", count++);
			simpDataObjects.put("Name", dataObjects.getUsername());
			simpDataObjects.put("Topic",(quiz !=null)? quiz.getQuizTopic():"");
			simpDataObjects.put("QuizName",(quiz !=null)? quiz.getQuizName():"");
			simpDataObjects.put("Score", dataObjects.getScore());
			simpDataObjects.put("Percentage", dataObjects.getPercentage());
			simplifiedObj.add(simpDataObjects);
		}
		return simplifiedObj;
	}
	
	/**
	 * Quiz Timer
	 * @param remainingTime
	 */
	private void startQuizTimer(AtomicInteger remainingTime) {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
        	int time=remainingTime.getAndDecrement();
        	int minutes=time / 60;
        	int seconds = time % 60;
            System.out.println("Time remaining: " + minutes +" Min " + seconds+" sec");
 
            if (time <= 0) {
                System.out.println("Quiz time's up!");
                System.out.println("Your Quiz is Submitted Automatically!!😊😊");
                if (executorService != null && !executorService.isShutdown()) {
                    executorService.shutdownNow();
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

	
}