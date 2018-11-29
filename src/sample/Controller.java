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
    private int X;
    private BigInteger BX;

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
                    if (MathProvider.isPrime(p) && (MathProvider.isPrime(q)))
                    {
                        e = Model.getE(f, d);
                        if (e.gcd(d).mod(f).compareTo(BigInteger.valueOf(1)) == 0)
                        {
                            OpenFile();
                            if (isFileOpened)
                            {
                                showAlert("AAAA", encodeHex("BSUIR"));
                                hash = SHA1.getHash(Model.getData());
                                System.out.println(encodeHex("BSUIR"));
                                showAlert("Easy", hash.toString(16));
                                System.out.println(hash.toString(16));
                                signature = MathProvider.pow(hash, d, r);
                                showAlert("Easy ^ 2", signature.toString());
                            }
                        }
                        else
                            showAlert("Wrong d", "d must satisfy '(e*d) mod φ(r) = 1' ");

                    }
                    else
                        showAlert("Wrong input", "P and Q must be prime");
                }
                else
                    showAlert("Wrong input", "Please enter the numbers");
            }
        });
    }

//    @FXML
//    private void OpenFileWithSHA1Signature()
//    {
//        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
//        fileChooser.setTitle("Open File");//Заголовок диалога
//        File file = fileChooser.showOpenDialog(STAGE);//Указываем текущую сцену
//        if ((file != null))
//        {
//            byte[] data = FileManager.ReadTxtToIntArrWithSHA1Signature(file);
//            Model.setDataSHA(data);
//            try {
//                readSHA1signature(file);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    @FXML
//    private void OpenFileWithSignature()
//    {
//
//        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
//        fileChooser.setTitle("Open File");//Заголовок диалога
//        File file = fileChooser.showOpenDialog(STAGE);//Указываем текущую сцену
//        if ((file != null))
//        {
//            //Open
//            int[] dataWithSign = logic.FileManager.ReadTxtToIntArrWithSignature(file);
//            int[] data = new int[dataWithSign.length - 1];
//            int len = data.length;
//            X = dataWithSign[dataWithSign.length - 1];
//            for (int i = 0; i < len; i++)
//                data[i] = dataWithSign[i];
//                Model.setData(data);
////            BigInteger hash = SHA11.getHash(Model.getData());
////            showAlert("Easy", hash.toString(16));
//            isFileOpened = true;
//        }
//        else
//        {
//            isFileOpened = false;
//            showAlert("Ошибка", "Что-то пошло не так. Выберите другой файл");
//        }
//    }

    private void readSHA1signature(File InputFile) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(InputFile), "UTF-8"));
        ArrayList<String> arrS = new ArrayList<String>();
        String s;
        while ((s = reader.readLine()) != null)
        {
            arrS.add(s);
        }
        String[] temp1 = arrS.get(arrS.size() - 1).split(" ");
        BigInteger sign;
        sign = new BigInteger(temp1[temp1.length - 1]);
        BX = sign;
    }

    @FXML
    private void OpenFile()
    {

        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Open File");//Заголовок диалога
        File file = fileChooser.showOpenDialog(STAGE);//Указываем текущую сцену
        if ((file != null))
        {
            Model.setData(FileManager.ReadByteArr(file));
            isFileOpened = true;
        }
        else
        {
            isFileOpened = false;
            showAlert("Ошибка", "Что-то пошло не так. Выберите другой файл");
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
            //TODO save to a file
            isFileOpened = true;
        }
        else
        {
            isFileOpened = false;
            showAlert("Ошибка", "Что-то пошло не так. Выберите другой файл");
        }
    }

    private void showAlert(String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Wrong file");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

}
