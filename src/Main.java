import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static class Encryption{
        static String charRU = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
        static String charWoutS = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЬЫЪЭЮЯ";
        static String charWSpace = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ ";
        public static String encryptCaesar(String text,int offset){
            StringBuilder textEncrypt = new StringBuilder();
            charRU=charRU.toUpperCase(Locale.ROOT);
            text=text.toUpperCase(Locale.ROOT);
            for(int i=0; i<text.length(); i++){
                int indC = charRU.indexOf(text.charAt(i)); //id символа в charRU
                int idEncrypChar = (charRU.length()+indC+offset)%charRU.length(); //id символа со сдвигом
                textEncrypt.append(charRU.charAt(idEncrypChar));
            }
            return textEncrypt.toString();
        }
        public static String encryptMultialphabetic(String text,String key){
            StringBuilder textEncrypt = new StringBuilder();
            text=text.toUpperCase(Locale.ROOT);
            key=key.toUpperCase(Locale.ROOT);
            int j=0;
            for(int i=0; i<text.length(); i++){
                if (text.charAt(i)!=' ') {
                    int indKey = charWoutS.indexOf(key.charAt((i-j) % key.length())); //id отнисительно charWoutS
                    int indText = charWoutS.indexOf(text.charAt(i));
                    int idEncrypChar = (indKey + indText) % charWoutS.length();
                    textEncrypt.append(charWoutS.charAt(idEncrypChar));
                }
                else {
                    j++;
                    textEncrypt.append(' ');
                }
            }
            return textEncrypt.toString();
        }
        public static String decryptMultialphabetic(String text,String key){ //н рцъш бщйщцеве
            StringBuilder textDecrypt = new StringBuilder();
            text=text.toUpperCase(Locale.ROOT);

            key=key.toUpperCase(Locale.ROOT);
            int j=0;
            for(int i=0; i<text.length(); i++){
                if (text.charAt(i)!=' ') {
                    int indKey = charWoutS.indexOf(key.charAt((i-j) % key.length())); //id отнисительно charWoutS
                    int indText = charWoutS.indexOf(text.charAt(i));
                    int idEncrypChar = 0;
                    int a =Math.abs(indText - indKey);

                    if(indText<indKey){
                        a=indText;
                        idEncrypChar=1;
                    }
                    idEncrypChar=idEncrypChar*(charWoutS.length()-indKey)+a;
                    textDecrypt.append(charWoutS.charAt(idEncrypChar));
                }
                else {
                    j++;
                    textDecrypt.append(' ');
                }
            }
            return textDecrypt.toString();
        }

    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("1) Цезарь");
        System.out.println("2) Многоалфавитный");
        System.out.print("Выберите пункт:");
        switch (in.nextLine()){
            case "1":{
                System.out.print("Введите текст:");
                String text=in.nextLine();
                System.out.print("Введите сдвиг:");
                int offset = in.nextInt();
                System.out.println(Encryption.encryptCaesar(text,offset));
                break;
            }
            case "2":{
                System.out.print("Введите текст:");
                String text=in.nextLine();
                System.out.print("Введите ключ:");
                String key = in.nextLine();
                System.out.println(Encryption.decryptMultialphabetic(text,key));
                break;
            }
        }


    }

}
