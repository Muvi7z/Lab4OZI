import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static class Encryption{
        static String charRU = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
        static String charWSym = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
        static String charWSpace = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ ";
        static String charFull= "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ 0123456789";
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
                    int indKey = charWSym.indexOf(key.charAt((i-j) % key.length())); //id отнисительно charWoutS
                    int indText = charWSym.indexOf(text.charAt(i));
                    int idEncrypChar = (indKey + indText) % charWSym.length();
                    textEncrypt.append(charWSym.charAt(idEncrypChar));
                }
                else {
                    j++;
                    textEncrypt.append(' ');
                }
            }
            return textEncrypt.toString();
        }
        public static String decryptMultiAlphabetic(String text, String key){ //н рцъш бщйщцеве || ЧУХУЮЯЬЦЭШЗЦШЫШУЭШШЫЬЧСЫЬЦШУАЬХУ ОЙЦ ГШГЦЕГЙПГЩЪЯАЭЖХН
            StringBuilder textDecrypt = new StringBuilder();
            text=text.toUpperCase(Locale.ROOT);

            key=key.toUpperCase(Locale.ROOT);
            int j=0;
            for(int i=0; i<text.length(); i++){
                if (text.charAt(i)!=' ') {
                    int indKey = charWSym.indexOf(key.charAt((i-j) % key.length())); //id отнисительно charWoutS
                    int indText = charWSym.indexOf(text.charAt(i));
                    int idEncrypChar = 0;
                    int a =Math.abs(indText - indKey);

                    if(indText<indKey){
                        a=indText;
                        idEncrypChar=1;
                    }
                    idEncrypChar=idEncrypChar*(charWSym.length()-indKey)+a;
                    textDecrypt.append(charWSym.charAt(idEncrypChar));
                }
                else {
                    j++;
                    textDecrypt.append(' ');
                }
            }
            return textDecrypt.toString();
        }
        public static String decryptBlockTrans(String text,String key) { //оасббр цяинаргдиди о в
            StringBuilder textDecrypt = new StringBuilder();
            text=text.toUpperCase(Locale.ROOT);
            key=key.toUpperCase(Locale.ROOT);
            StringBuilder sorted = key.chars()
                    .sorted()
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append);
            int[] sortKey = new int[key.length()];
            int n = key.length();
            for (int i = 0; i<n; i++){
                sortKey[i]=sorted.indexOf(String.valueOf(key.charAt(i)));
                sorted.setCharAt(sortKey[i], '-');
            }
            n =text.length();
            for (int i = 0; i<n; i++){
                textDecrypt.append(text.charAt(((i/key.length())*key.length())+sortKey[i%key.length()]));
            }
            return textDecrypt.toString();
        }
        public static String decryptGamma(String text,String key) { //свиьё6ёпйр0з122м
            text=text.toUpperCase(Locale.ROOT);
            key=key.toUpperCase(Locale.ROOT);
            StringBuilder textDecrypt = new StringBuilder();
            for (int i=0; i<text.length(); i++){
                int C = charFull.indexOf(text.charAt(i))+1;
                int G = charFull.indexOf(key.charAt(i%key.length()))+1;
                int T = (C - G +charFull.length())%charFull.length();
                T = T==0?44:T;
                textDecrypt.append(charFull.charAt(T-1));
            }
            return textDecrypt.toString();
        }
        public static String encryptBiteSum(String text,String key) {
            text=text.toUpperCase(Locale.ROOT);
            key=key.toUpperCase(Locale.ROOT);
            int[] biteText = new int[6];
            int[] biteKey = new int[6];
            StringBuilder textDecrypt = new StringBuilder();
            for (int j=0; j<text.length(); j++){
                int idText = charFull.indexOf(text.charAt(j))+1;
                int idKey = charFull.indexOf(key.charAt(j%key.length()))+1;
                int id=0;
                for(int i=5; i>=0; i--){
                    biteKey[i]=idKey%2;
                    biteText[i]=idText%2;
                    idKey/=2;
                    idText/=2;
                    id+= (((biteKey[i]^biteText[i])+1)%2)*Math.pow(2,(5-i));
                }
                textDecrypt.append(charFull.charAt(id-1));
            }
            return textDecrypt.toString();
        }
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("1) Цезарь");
        System.out.println("2) Многоалфавитный");
        System.out.println("3) Блочная перестановка");
        System.out.println("4) Гаммирование");
        System.out.println("5) побитовое сложение сообщения и гаммы");
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
                System.out.println(Encryption.decryptMultiAlphabetic(text,key));
                break;
            }
            case "3":{
                System.out.print("Введите текст:");
                String text=in.nextLine();
                System.out.print("Введите ключ:");
                String key = in.nextLine();
                System.out.println(Encryption.decryptBlockTrans(text,key));
                break;
            }
            case "4":{
                System.out.print("Введите текст:");
                String text=in.nextLine();
                System.out.print("Введите ключ:");
                String key = in.nextLine();
                System.out.println(Encryption.decryptGamma(text,key));
                break;
            }
            case "5":{
                System.out.print("Введите текст:");
                String text=in.nextLine();
                System.out.print("Введите ключ:");
                String key = in.nextLine();
                System.out.println(Encryption.encryptBiteSum(text,key));
                break;
            }
        }


    }

}
