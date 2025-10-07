package th.nguyenphananhtai.simplequiz;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView tvQuestion, tvScore, tvQuestionNumber;
    private Button btnOption1, btnOption2, btnOption3, btnOption4, btnNext;
    private LinearLayout layoutOptions;

    private ArrayList<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int correctAnswer;
    private boolean answered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo views
        tvQuestion = findViewById(R.id.tvQuestion);
        tvScore = findViewById(R.id.tvScore);
        tvQuestionNumber = findViewById(R.id.tvQuestionNumber);
        btnOption1 = findViewById(R.id.btnOption1);
        btnOption2 = findViewById(R.id.btnOption2);
        btnOption3 = findViewById(R.id.btnOption3);
        btnOption4 = findViewById(R.id.btnOption4);
        btnNext = findViewById(R.id.btnNext);
        layoutOptions = findViewById(R.id.layoutOptions);

        // Tạo danh sách câu hỏi
        generateQuestions();

        // Hiển thị câu hỏi đầu tiên
        showQuestion();

        // Xử lý sự kiện click cho các nút đáp án
        View.OnClickListener optionClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    checkAnswer((Button) v);
                }
            }
        };

        btnOption1.setOnClickListener(optionClickListener);
        btnOption2.setOnClickListener(optionClickListener);
        btnOption3.setOnClickListener(optionClickListener);
        btnOption4.setOnClickListener(optionClickListener);
    }

    private void generateQuestions() {
        questions = new ArrayList<>();
        Random rand = new Random();

        // Tạo 10 câu hỏi toán ngẫu nhiên
        for (int i = 0; i < 10; i++) {
            int num1 = rand.nextInt(20) + 1;
            int num2 = rand.nextInt(20) + 1;
            int operation = rand.nextInt(4); // 0: +, 1: -, 2: *, 3: /

            String questionText = "";
            int answer = 0;

            switch (operation) {
                case 0: // Cộng
                    questionText = num1 + " + " + num2 + " = ?";
                    answer = num1 + num2;
                    break;
                case 1: // Trừ
                    if (num1 < num2) {
                        int temp = num1;
                        num1 = num2;
                        num2 = temp;
                    }
                    questionText = num1 + " - " + num2 + " = ?";
                    answer = num1 - num2;
                    break;
                case 2: // Nhân
                    num1 = rand.nextInt(10) + 1;
                    num2 = rand.nextInt(10) + 1;
                    questionText = num1 + " × " + num2 + " = ?";
                    answer = num1 * num2;
                    break;
                case 3: // Chia
                    num2 = rand.nextInt(9) + 1;
                    answer = rand.nextInt(10) + 1;
                    num1 = num2 * answer;
                    questionText = num1 + " ÷ " + num2 + " = ?";
                    break;
            }

            questions.add(new Question(questionText, answer));
        }
    }

    private void showQuestion() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        tvQuestion.setText(currentQuestion.getQuestion());
        tvQuestionNumber.setText("Câu " + (currentQuestionIndex + 1) + "/" + questions.size());
        tvScore.setText("Điểm: " + score);

        correctAnswer = currentQuestion.getAnswer();

        // Tạo 3 đáp án sai
        ArrayList<Integer> options = new ArrayList<>();
        options.add(correctAnswer);

        Random rand = new Random();
        while (options.size() < 4) {
            int wrongAnswer = correctAnswer + rand.nextInt(21) - 10;
            if (wrongAnswer != correctAnswer && !options.contains(wrongAnswer) && wrongAnswer >= 0) {
                options.add(wrongAnswer);
            }
        }

        // Xáo trộn các đáp án
        Collections.shuffle(options);

        // Hiển thị các đáp án
        btnOption1.setText(String.valueOf(options.get(0)));
        btnOption2.setText(String.valueOf(options.get(1)));
        btnOption3.setText(String.valueOf(options.get(2)));
        btnOption4.setText(String.valueOf(options.get(3)));

        // Reset màu nút
        resetButtonColors();
    }

    private void checkAnswer(Button selectedButton) {
        answered = true;
        int selectedAnswer = Integer.parseInt(selectedButton.getText().toString());

        if (selectedAnswer == correctAnswer) {
            selectedButton.setBackgroundColor(Color.parseColor("#4CAF50"));
            score += 10;
            tvScore.setText("Điểm: " + score);
            Toast.makeText(this, "Chính xác! 🎉", Toast.LENGTH_SHORT).show();
        } else {
            selectedButton.setBackgroundColor(Color.parseColor("#F44336"));
            highlightCorrectAnswer();
            Toast.makeText(this, "Sai rồi! 😢", Toast.LENGTH_SHORT).show();
        }

        // Tự động chuyển câu hỏi sau 1 giây
        selectedButton.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    answered = false;
                    showQuestion();
                } else {
                    showFinalScore();
                }
            }
        }, 1000);
    }

    private void highlightCorrectAnswer() {
        if (Integer.parseInt(btnOption1.getText().toString()) == correctAnswer) {
            btnOption1.setBackgroundColor(Color.parseColor("#4CAF50"));
        } else if (Integer.parseInt(btnOption2.getText().toString()) == correctAnswer) {
            btnOption2.setBackgroundColor(Color.parseColor("#4CAF50"));
        } else if (Integer.parseInt(btnOption3.getText().toString()) == correctAnswer) {
            btnOption3.setBackgroundColor(Color.parseColor("#4CAF50"));
        } else if (Integer.parseInt(btnOption4.getText().toString()) == correctAnswer) {
            btnOption4.setBackgroundColor(Color.parseColor("#4CAF50"));
        }
    }

    private void resetButtonColors() {
        int defaultColor = Color.parseColor("#2196F3");
        btnOption1.setBackgroundColor(defaultColor);
        btnOption2.setBackgroundColor(defaultColor);
        btnOption3.setBackgroundColor(defaultColor);
        btnOption4.setBackgroundColor(defaultColor);
    }

    private void showFinalScore() {
        tvQuestion.setText("Hoàn thành!");
        tvQuestionNumber.setText("Kết quả cuối cùng");
        tvScore.setText("Điểm: " + score + "/" + (questions.size() * 10));

        // Ẩn các nút đáp án và hiển thị nút chơi lại
        btnOption1.setVisibility(View.GONE);
        btnOption2.setVisibility(View.GONE);
        btnOption3.setVisibility(View.GONE);
        btnOption4.setVisibility(View.GONE);

        btnNext.setVisibility(View.VISIBLE);
        btnNext.setText("Chơi lại");
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartQuiz();
            }
        });
    }

    private void restartQuiz() {
        currentQuestionIndex = 0;
        score = 0;
        answered = false;
        generateQuestions();

        // Hiển thị lại các nút đáp án
        btnOption1.setVisibility(View.VISIBLE);
        btnOption2.setVisibility(View.VISIBLE);
        btnOption3.setVisibility(View.VISIBLE);
        btnOption4.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.GONE);

        showQuestion();
    }

    // Class Question
    private class Question {
        private String question;
        private int answer;

        public Question(String question, int answer) {
            this.question = question;
            this.answer = answer;
        }

        public String getQuestion() {
            return question;
        }

        public int getAnswer() {
            return answer;
        }
    }
}