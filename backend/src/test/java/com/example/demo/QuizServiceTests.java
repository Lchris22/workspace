
package com.example.demo;
 
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

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
import com.example.demo.entity.Topic;
import com.example.demo.service.QuizService;
 
@SpringBootTest
//@AutoConfigureTestDatabase(connection= EmbeddedDatabaseConnection.H2)
public class QuizServiceTests {
 
    @Autowired
    private QuizService quizService;
 
    @MockBean
    private QuizRepository quizRepository;
 
    @MockBean
    private ResultRepository resultRepository;
    /**  QuizService Test cases  **/
   
    @Test
    public void QuizService_GetQuizById_ReturnsQuizDto() throws Exception {
 
        // create question and quiz
        List<Questions> question_list = DummyData.generate20Questions();
        Quiz quiz123 = DummyData.createQuiz("Python Basics", 1, "ACTIVE", "PYTHON", question_list, 1, "lenin");
 
        // stub
        when(quizRepository.findById(1)).thenReturn(Optional.ofNullable(quiz123));
 
        // saving quiz using quiz service
        Quiz savedQuiz = quizService.getQuizById(1);
 
        assertThat(savedQuiz).isNotNull();
    }
 
    @Test
    public void QuizService_GetQuizWhenIdDoesntExist_ReturnsNull() throws Exception {
       
        //stub
        when(quizRepository.findById(1)).thenReturn(Optional.ofNullable(null));
        assertThrows(QuizNotFoundException.class,()-> quizService.getQuizById(1));
    }
 
//    @Test
//    public void QuizService_GetQuizByUsername_ReturnsAllRespectiveQuizzes() throws QuizNotFoundException {
// 
//        // create question and quizzes
//        List<Questions> question_list = DummyData.generate20Questions();
//        Quiz quiz2 = DummyData.createQuiz("Java Basics", 1, "ACTIVE", "JAVA_CORE", question_list, 1, "pratibh");
//        Quiz quiz3 = DummyData.createQuiz("CPP Basics", 1, "ACTIVE", "CPP", question_list, 1, "pratibh");
//        List<Quiz> quiz_list = new ArrayList<>(Arrays.asList(quiz2, quiz3));
//        Authentication authentication = null;
//        // stub
//        String username="Vishwajeet";
//        when(authentication.getName()).thenReturn(username);
//        when(quizRepository.findByUsername(username)).thenReturn(quiz_list);
//        // calling get quiz by username
//        List<Quiz> quizzesByUsername = quizService.getQuizByUsername();
//        // asserts
//        System.out.println();
//        assertEquals("pratibh", quizzesByUsername.get(0).getUsername());
//        assertEquals("pratibh", quizzesByUsername.get(1).getUsername());
//    }
 
    @Test
    public void QuizService_GetQuizByUsernameWhenDoesntExist_ReturnsQuizNotFoundException() throws Exception {
       
        //stub
        when(quizRepository.findByUsername("pratibh")).thenReturn(new ArrayList<Quiz>() );
       
        //calling get quiz by username
        assertThrows(NullPointerException.class,()-> quizService.getQuizByUsername());
    }
 
    @Test
    public void QuizService_GetQuizByTopic_ReturnsAllRespectiveQuizzes() throws Exception {
        // Creating 2 quizzes of topic JAVA_CORE
        List<Questions> question_list = DummyData.generate20Questions();
        Quiz quiz2 = DummyData.createQuiz("Java Basics", 1, "ACTIVE", "JAVA_CORE", question_list, 1, "pratibh");
        Quiz quiz3 = DummyData.createQuiz("Java Advance", 1, "ACTIVE", "JAVA_CORE", question_list, 1, "lenin");
        List<Quiz> quiz_list = new ArrayList<>(Arrays.asList(quiz2, quiz3));
        Map<String,Object> quiz_map = new HashMap<>();
        // stub
        when(quizRepository.findByquizTopic("JAVA_CORE")).thenReturn(quiz_list);
        List<Map<String,Object>> quizzesByTopic = quizService.getQuizByquizTopic("JAVA_CORE");
        assertEquals(2,quizzesByTopic.size());
    }
 
    @Test
    public void QuizService_GetQuizByTopicWhenDoesntExist_ReturnsQuizNotFoundException() {
       
        //stub
        when(quizRepository.findByquizTopic("JAVA_CORE")).thenReturn(Collections.emptyList());
       
        //calling get quiz by username
        assertThrows(QuizNotFoundException.class,()-> quizService.getQuizByquizTopic("JAVA_CORE"));
    }
 
    @Test
    public void QuizService_AddValidQuiz_ReturnsTheQuizObject() throws Exception {
        // Creating a Quiz with valid Data
        List<Questions> question_list = DummyData.generate20Questions();
        Quiz quiz1 = DummyData.createQuiz("Java Advance", 2, "ACTIVE", "JAVA_CORE", question_list, 20, "lenin");
        when(quizRepository.save(quiz1)).thenReturn(quiz1);
 
        Quiz returnedQuiz = quizService.addQuiz(quiz1);
        assertEquals("lenin", returnedQuiz.getUsername());
        assertEquals(40, returnedQuiz.getTotal_duaration_of_quiz());
    }
 
    /** Adding a Quiz with 19 Questions **/
    @Test
    public void QuizService_AddQuizWithInValidNoOfQuestions_ReturnsException() throws Exception {
        // Making a Quiz with 19 questions
        List<Questions> question_list = DummyData.generate20Questions();
        question_list.remove(0);
        assertEquals(19, question_list.size());
        Quiz quiz1 = DummyData.createQuiz("Java Advance", 2, "ACTIVE", "JAVA_CORE", question_list, 19, "lenin");
 
        assertThrows(QuestionsLengthException.class, () -> quizService.addQuiz(quiz1));
    }
 
    /** Adding a Quiz with 21 Questions **/
    @Test
    public void QuizService_AddQuizWithInValidNoOfQuestions2_ReturnsException() throws Exception {
        // Making a Quiz with 21 questions
        List<Questions> question_list = DummyData.generate20Questions();
        question_list.add(DummyData.createQuestion("What is the capital of France?", "Paris", "London", "Berlin",
                "Madrid", 1));
        assertEquals(21, question_list.size());
        Quiz quiz1 = DummyData.createQuiz("Java Advance", 2, "ACTIVE", "JAVA_CORE", question_list, 19, "lenin");
 
        assertThrows(QuestionsLengthException.class, () -> quizService.addQuiz(quiz1));
    }
 
    /**Update the status of the quiz */
    @Test
    public void QuizService_updateStatusOfQuiz_ChangesStatus() throws Exception {
        // Making a Quiz with 21 questions
 
        List<Questions> question_list = DummyData.generate20Questions();
        Quiz quiz1 = DummyData.createQuiz("Java Advance", 2, "ACTIVE", "JAVA_CORE", question_list, 19, "lenin");
        quiz1.setQuizId(1);
 
        when(quizRepository.findById(1)).thenReturn(Optional.ofNullable(quiz1));
        when(quizRepository.save(quiz1)).thenReturn(quiz1);
 
        assertNotNull(quizService.updateStatusOfQuiz(1, "ACTIVE"));
    }
 
    /**Update the status of the quiz when the quiz does not exist*/
    @Test
    public void QuizService_updateStatusOfQuizWhenDoesntExist_ReturnsQuizNotFoundException()  throws Exception{
        //Making a Quiz with 21 questions
       
        when(quizRepository.findById(1)).thenReturn(Optional.ofNullable(null));
       
        assertThrows(QuizNotFoundException.class, () -> quizService.updateStatusOfQuiz(1,"ACTIVE"));
    }
    
    /**Get the quiz for trainers without questions*/
//    @Test
//    public void getQuizWithoutQuestions_ForTrainers() {
//    	List<Questions> question_list = new ArrayList<Questions>();
//        question_list.add(DummyData.createQuestion("Number of datatypes in java?", "4", "6", "7",
//                "8", "8"));
//        question_list.add(DummyData.createQuestion("_____ is used to find and fix bugs in the Java programs.", "JVM", "JRE", "JDK",
//                "JDB", "JDB"));
//    	 Quiz quiz1 = DummyData.createQuiz("Java Advance", 2, "ACTIVE", "JAVA_CORE", question_list, 19, "Vishwajeet");
//    	 
//    	 
//    	 List<Quiz> quizList = new ArrayList<Quiz>();
//    	 quizList.add(quiz1);
//    	 when(quizRepository.findAll()).thenReturn(quizList);
//    	 
//    	 List<Quiz> requiredQuizList = quizService.getQuizzesForTrainer();
//    	 Quiz requiredQuiz = requiredQuizList.get(0);
//    	 assertEquals(0, requiredQuiz.getListOfquestions().size());
//    }
//    
    /**Student answer the quiz*/
    @Test
    public void QuizService_takeQuiz_LetsStudentsAnswerQuiz() throws Exception{
    	List<Questions> question_list = DummyData.generate20Questions();
        Quiz quiz1 = DummyData.createQuiz("Java Advance", 2, "ACTIVE", "JAVA_CORE", question_list, 19, "lenin");
        quiz1.setQuizId(49);
        
        when(quizRepository.getQuizForStudent(49)).thenReturn(quiz1);
    	ResponseEntity<List<QuestionWrapper>> quiz=quizService.takeQuiz(49,"prathm");
    	 List<QuestionWrapper> quizQuestions = quiz.getBody();
    	 assertEquals(20, quizQuestions.size());
    }
    
    @Test
    public void QuizService_takeQuiz_ReturnsQuizAnsweredException() throws Exception{
        when(resultRepository.hasAnswered(49, "prathm")).thenReturn(49);
    	 assertThrows(QuizAnswered.class, () -> quizService.takeQuiz(49,"prathm"));
    }
    
    
    /**Calculate the result when student have answered the quiz*/
//    @Test 
//    public void calculateResult_ResultOfstudentPassingTheQuiz() {
//    	List<Questions> question_list = new ArrayList<Questions>();
//    	
//        question_list.add(new Questions(1,"Number of datatypes in java?", "4", "6", "7","8",4,new Quiz()));
//        question_list.add(new Questions(2, "_____ is used to find and fix bugs in the Java programs.", "JVM", "JRE", "JDK",
//                "JDB", 4,null));
//        Optional<Quiz> quiz1 = Optional.of(new Quiz(100,"Java Advance", 2,"JAVA_CORE" ,"ACTIVE", 4, 2,question_list , "Vishwajeet"));
//	   	when(quizRepository.findById(2)).thenReturn(quiz1);
//	   	
//	   	Response res = new Response();
//	   	res.setQuestionId(1);
//	   	res.setResponse(4);
//	   	
//	   	Response res1 = new Response();
//	   	res1.setQuestionId(2);
//	   	res1.setResponse(4);
//	   	
//	   	List<Response> listOfResponses = new ArrayList<>();
//	   	listOfResponses.add(res);
//	   	listOfResponses.add(res1);
//	   	
//	   	Result result = new Result();
//	   	result.setListOfResponses(listOfResponses);
//	   	result.setQuizId(100);
//	   	result.setUsername("vishwajeet");
//	   	ResponseEntity<String> HttpResponse = quizService.calculateResult(2, result);
//	   	String resultValue = HttpResponse.getBody();
//	   	assertEquals("Correct answers: 2\nCongratulations! You have passed with 100.0% correct answers.", resultValue);
//    }
    
    /**Get all the topics present in the db*/
    @Test
    public void getAllTopics(){
    	Set<String> topicSet = new HashSet<>();
    	topicSet.add("CPP");
    	topicSet.add("JAVA_CORE");
    	topicSet.add("PYTHON");
    	topicSet.add("UI");
    	when(quizRepository.getAllTopics()).thenReturn(topicSet);
    	Set<String> topics = quizService.getAllTopics();
    	assertEquals(4, topics.size());
    }


//    @Test
//    public void QuizService_getQuizzessForStudent_ReturnsAllQuizzes() {
//        // create question and quizzes
//        List<Questions> question_list = DummyData.generate20Questions();
//        Quiz quiz1 = DummyData.createQuiz("Python Basics", 1, "ACTIVE", "PYTHON", question_list, 1, "lenin");
//        Quiz quiz2 = DummyData.createQuiz("Java Basics", 1, "ACTIVE", "JAVA_CORE", question_list, 1, "pratibh");
//        Quiz quiz3 = DummyData.createQuiz("Java Advanced", 1, "ACTIVE", "JAVA_CORE", question_list, 1, "Vishwajeet");
//        // list of 3 quizes
//        List<Quiz> list_fromRepository = Arrays.asList(quiz1,quiz2,quiz3);
//        
//        Map<String, Object> myMap1 = new HashMap<>() {{put("Python Basics", quiz1);}};
//        Map<String, Object> myMap2 = new HashMap<>() {{put("Java Basics", quiz2);}};
//        Map<String, Object> myMap3 = new HashMap<>() {{put("Java Advanced",quiz3);}};
//        
//       
//      
//        // stub
//        when(quizRepository.findAll()).thenReturn(list_fromRepository);
//        // calling getquiz
//        List<Map<String,Object>> allQuizzes = quizService.getQuizzessForStudent();
// 
//        assertThat(allQuizzes.size() == 3);
//    }
    
    /**Get the quiz under the specified topic*/
    @Test
    public void getQuizByTopicName() throws Exception{
    	List<Questions> questionList = new ArrayList<>();
    	Quiz quiz1 = new Quiz(100,"Java Advance", 2,"JAVA_CORE" ,"ACTIVE", 4, 2,questionList , "Vishwajeet");
    	Quiz quiz2 = new Quiz(101,"OOPS", 5,"JAVA_CORE" ,"ACTIVE", 10, 2,questionList , "Vaibhav");
    	Quiz quiz3 = new Quiz(102,"C++", 5,"CPP" ,"ACTIVE", 10, 2,questionList , "Lenin");
    	List<Quiz> JavaQuiz = Arrays.asList(quiz1,quiz2,quiz3);
    	
    	
    	when(quizRepository.findByquizTopic("JAVA_CORE")).thenReturn(JavaQuiz);
    	
    	List<Map<String,Object>> responseQuiz = quizService.getQuizByquizTopic("JAVA_CORE");
    	
    	assertEquals(3,responseQuiz.size());
    }

    @Test
    public void QuizService_getQuizByquizTopics_returnsQuiz() throws Exception{
    	List<Questions> questionList = new ArrayList<>();
    	Quiz quiz1 = new Quiz(100,"Java Advance", 2,"JAVA_CORE" ,"ACTIVE", 4, 2,questionList , "Vishwajeet");
    	Quiz quiz2 = new Quiz(101,"OOPS", 5,"JAVA_CORE" ,"ACTIVE", 10, 2,questionList , "Vaibhav");
    	Quiz quiz3 = new Quiz(102,"C++", 5,"CPP" ,"ACTIVE", 10, 2,questionList , "Lenin");
    	List<Quiz> JavaQuiz = Arrays.asList(quiz1,quiz2);
    	
    	
    	when(quizRepository.findByquizTopic("JAVA_CORE")).thenReturn(JavaQuiz);
    	
    	List<LinkedHashMap<String, Object>> responseQuiz = quizService.getQuizByquizTopics("JAVA_CORE");
    	
    	assertEquals(responseQuiz.size(), JavaQuiz.size());
    }
    
    /**Get the top 3 Students according to the score in a particular quiz*/
    @Test 
    public void getLeaderBoard() {
    	Result result1 = new Result(1,100,new ArrayList<Response>(),"Pratibh",92.0,70);
    	Result result2 = new Result(2,100,new ArrayList<Response>(),"Vishwajeet",98,80);
    	Result result3 = new Result(3,100,new ArrayList<Response>(),"Lenin",87.0,75);
    	List<Result> topperList = Arrays.asList(result2,result1,result3);
    	when(resultRepository.showLeaderBoard(100)).thenReturn(topperList);
    	
    	List<LinkedHashMap<String, Object>> leaderboard = quizService.showLeaderBoard(100);
    	
    	assertEquals(1,leaderboard.get(0).get("Rank"));
    	assertEquals("Pratibh",leaderboard.get(1).get("Name"));
    	assertEquals(87.0,leaderboard.get(2).get("Percentage"));
    	
    }
}