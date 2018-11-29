package sample;

import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.*;

import static logic.SHA1.encodeHex;

public class Controller {

    private boolean isFileOpened = false;
    private BigInteger X;

    @FXML
    private ResourceBundle resources;

    @FXML
    public static Stage STAGE;

    @FXML
    private URL location;

    @FXML
    private TextField edtP;

    @FXML
    private TextField edtQ;

    @FXML
    private TextField edtB;

    @FXML
    private TextArea arResults;

    @FXML
    private TextArea arHash;

    @FXML
    private Button btnGenerate;

    @FXML
    private Button btnCheck;

    @FXML
    private Label lblResult;

    @FXML
    void initialize() {
        edtP.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0123456789]{0,256}"))
            {
                edtP.setText(newValue.replaceAll("[^[0123456789]]", ""));
            }
        });

        edtQ.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0123456789]{0,256}"))
            {
                edtQ.setText(newValue.replaceAll("[^[0123456789]]", ""));
            }
        });

        edtB.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0123456789]{0,256}"))
            {
                edtB.setText(newValue.replaceAll("[^[0123456789]]", ""));
            }
        });

        btnGenerate.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if ((edtP.getText().length() > 0) && (edtQ.getText().length() > 0) && (edtB.getText().length() > 0))
                {
                    BigInteger p = new BigInteger(edtP.getText());
                    BigInteger q = new BigInteger(edtQ.getText());
                    BigInteger d = new BigInteger(edtB.getText());
                    BigInteger r = p.multiply(q);
                    BigInteger f = (p.subtract(BigInteger.valueOf(1))).multiply(q.subtract(BigInteger.valueOf(1)));
                    BigInteger e, hash, signature;
                    if (MathProvider.isPrime(p) && (MathProvider.isPrime(q)) && (d.compareTo(BigInteger.ZERO) != 0))
                    {
                        e = Model.getE(f, d);
                        if (e.gcd(d).mod(f).compareTo(BigInteger.valueOf(1)) == 0)
                        {
                            OpenFile();
                            if (isFileOpened)
                            {
                                //showAlert("AAAA", encodeHex("BSUIR"));
                                hash = SHA1.getHash(Model.getData());
                                System.out.println(encodeHex("BSUIR"));
                                //showAlert("Easy", hash.toString(16));
                                System.out.println(hash.toString(16));
                                signature = MathProvider.pow(hash, d, r);
                                System.out.println(signature.toString(16));
                                X = signature;
                                arHash.setText("Hash(10) = " + hash.toString() + "\nHash(16) = " + hash.toString(16));
                                arResults.setText("R = " + r.toString() + "\nSecret key = " + e.toString() + "\nSignature = " + signature.toString());
                                //showAlert("Easy ^ 2", signature.toString());
                                SaveFile();
                            }
                        }
                        else
                            showAlert("Error","Wrong d", "d must satisfy '(e*d) mod φ(r) = 1' ");

                    }
                    else
                        showAlert("Error","Wrong input", "P and Q must be prime. B must be greater than zero");
                }
                else
                    showAlert("Error","Wrong input", "Please enter the numbers");
            }
        });

        btnCheck.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if ((edtP.getText().length() > 0) && (edtQ.getText().length() > 0) && (edtB.getText().length() > 0))
                {
                    BigInteger p = new BigInteger(edtP.getText());
                    BigInteger q = new BigInteger(edtQ.getText());
                    BigInteger d = new BigInteger(edtB.getText());
                    BigInteger r = p.multiply(q);
                    BigInteger f = (p.subtract(BigInteger.valueOf(1))).multiply(q.subtract(BigInteger.valueOf(1)));
                    BigInteger e, hash, s;
                    if (MathProvider.isPrime(p) && (MathProvider.isPrime(q)) && (d.compareTo(BigInteger.ZERO) != 0))
                    {
                        e = Model.getE(f, d);
                        if (e.gcd(d).mod(f).compareTo(BigInteger.valueOf(1)) == 0)
                        {
                            OpenFileWithSignature();

                            if (isFileOpened)
                            {
                                hash = SHA1.getHash(Model.getData());
                                s = MathProvider.pow(X, e, r);
                                if (s.compareTo(hash) == 0) {
                                    showAlert("EDS check", "Correct", "Signature is correct");
                                    lblResult.setText("Correct");
                                }
                                else {
                                    showAlert("EDS check", "Incorrect", "Signature is incorrect");
                                    lblResult.setText("Incorrect");
                                    arHash.setText("Hash(10) = " + hash.toString() + "\nHash(16) = " + hash.toString(16));
                                    arResults.setText("R = " + r.toString() + "\nSecret key = " + e.toString() + "\nSignature = " + X.toString());
                                }
                            }
                        }
                        else
                            showAlert("Error","Wrong d", "d must satisfy '(e*d) mod φ(r) = 1' ");

                    }
                    else
                        showAlert("Error","Wrong input", "P and Q must be prime. B must be greater than zero");
                }
                else
                    showAlert("Error","Wrong input", "Please enter the numbers");
            }
        });
    }

    @FXML
    private void OpenFile()
    {

        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Open File");//Заголовок диалога
        File file = fileChooser.showOpenDialog(STAGE);//Указываем текущую сцену
        if ((file != null))
        {
            Model.setData(FileManager20.ReadByteArr(file));
            isFileOpened = true;
        }
        else
        {
            isFileOpened = false;
            showAlert("Wrong file","Ошибка", "Что-то пошло не так. Выберите другой файл");
        }
    }

    @FXML
    private void OpenFileWithSignature()
    {

        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Open File");//Заголовок диалога
        File file = fileChooser.showOpenDialog(STAGE);//Указываем текущую сцену
        if ((file != null))
        {
            Model.setData(FileManager20.ReadByteArrWithSignature(file));
            X = FileManager20.ReadSignature(file);
            isFileOpened = true;
        }
        else
        {
            isFileOpened = false;
            showAlert("Wring file","Ошибка", "Что-то пошло не так. Выберите другой файл");
        }
    }

    @FXML
    private void SaveFile()
    {

        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Save File");//Заголовок диалога
        File file = fileChooser.showOpenDialog(STAGE);//Указываем текущую сцену
        if ((file != null))
        {
            String strSignDec = X.toString();
            FileManager20.SaveByteArr(Model.getData(), file);
            FileManager20.AppendStr(strSignDec, file);
            isFileOpened = true;
        }
        else
        {
            isFileOpened = false;
            showAlert("Wring file","Ошибка", "Что-то пошло не так. Выберите другой файл");
        }
    }

    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

}
