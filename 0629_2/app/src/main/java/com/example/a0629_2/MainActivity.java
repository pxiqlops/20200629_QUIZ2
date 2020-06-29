package com.example.a0629_2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView countLabel;
    private ImageView questionImage;
    private Button answerBtn1, answerBtn2, answerBtn3, answerBtn4;

    private String rightAnswer;
    private int rightAnswerCount = 0;
    private int quizCount = 1;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();

    String quizData[][] = {
            // {"画像名", "正解", "選択肢１", "選択肢２", "選択肢３"}
            {"apple", "リンゴ", "イチゴ", "ブドウ", "ナシ"},
            {"banana", "バナナ", "きゅうり", "レモン", "メロン"},
            {"ichigo", "イチゴ", "リンゴ", "ラズベリー", "スイカ"},
            {"melon", "メロン", "スイカ", "かぼちゃ", "リンゴ"},
            {"suika", "スイカ", "メロン", "オレンジ", "バナナ"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countLabel = findViewById(R.id.countLabel);
        questionImage = findViewById(R.id.questionImage);
        answerBtn1 = findViewById(R.id.answerBtn1);
        answerBtn2 = findViewById(R.id.answerBtn2);
        answerBtn3 = findViewById(R.id.answerBtn3);
        answerBtn4 = findViewById(R.id.answerBtn4);

        // クイズデータquizDataからクイズ出題用のquizArrayを作成する
        for (int i = 0; i < quizData.length; i++) {
            // 新しいArrayListを用意
            ArrayList<String> tmpArray = new ArrayList<>();

            // クイズデータを追加
            tmpArray.add(quizData[i][0]); // 画像名
            tmpArray.add(quizData[i][1]); // 正解
            tmpArray.add(quizData[i][2]); // 選択肢１
            tmpArray.add(quizData[i][3]); // 選択肢２
            tmpArray.add(quizData[i][4]); // 選択肢３

            // tmpArrayをquizArrayに追加
            quizArray.add(tmpArray);
        }

        showNextQuiz();
    }

    public void showNextQuiz() {
        // クイズカウントラベルを更新
        countLabel.setText("Q" + quizCount);

        // ランダムな数字を取得
        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());

        // randomNumを使って、quizArrayからクイズを一つ取り出す
        ArrayList<String> quiz = quizArray.get(randomNum);

        // 問題画像をセット
        questionImage.setImageResource(
                getResources().getIdentifier(quiz.get(0), "drawable", getPackageName())
        );

        // 正解をrightAnswerをセット
        rightAnswer = quiz.get(1);

        // クイズ配列から画像名を削除
        quiz.remove(0);

        // 正解と選択肢３つをシャッフル
        Collections.shuffle(quiz);

        // 回答ボタンに正解と選択肢３つをセット
        answerBtn1.setText(quiz.get(0));
        answerBtn2.setText(quiz.get(1));
        answerBtn3.setText(quiz.get(2));
        answerBtn4.setText(quiz.get(3));

        // このクイズをquizArrayから削除
        quizArray.remove(randomNum);
    }

    public void checkAnswer(View view) {

        // どの回答ボタンが押されたか
        Button answerBtn = findViewById(view.getId());
        String btnText = answerBtn.getText().toString();

        String alertTitle;

        if (btnText.equals(rightAnswer)) {
            // 正解
            alertTitle = "正解！";
            rightAnswerCount++;

        } else {
            // 不正解
            alertTitle = "不正解...";
        }

        // ダイアログ作成
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle);
        builder.setMessage("答え：" + rightAnswer);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (quizArray.size() < 1) {
                    // クイズを全て出題したら結果を表示
                    showResult();
                } else {
                    quizCount++;
                    // 次の問題を表示
                    showNextQuiz();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    public void showResult() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("結果");
        //builder.setMessage(rightAnswerCount + " / " + quizCount);
        builder.setMessage(quizCount + "問中" + rightAnswerCount + "問正解");
        builder.setPositiveButton("もう一度", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                recreate();
            }
        });
        builder.setNegativeButton("終了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.show();
    }
}