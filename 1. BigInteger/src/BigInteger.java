import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BigInteger {
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "입력이 잘못되었습니다.";

    // implement this
    public static final Pattern EXPRESSION_PATTERN = Pattern.compile("");

    //bigInteger: 입력받은 값을 char array 로 저장하기
    //fullInput 의 크기 할당하기
    //full input 은 숫자 한 개의 string
    public char[] fullInput;
    public String Input_string;

    public BigInteger(String s) {
        //수 한개는 최대 100자리이다.
        this.fullInput = s.toCharArray();
        this.Input_string = s;
    }

    public boolean BiggerNumber(BigInteger number2){
        //big is true when number1>=number2
        String num1 = new String(fullInput);
        String num2 = new String(number2.fullInput);
        boolean big = true;
        int num1_int = Integer.parseInt(num1);
        int num2_int = Integer.parseInt(num2);
        if (num1_int<num2_int){
            big=false;
        }
        return big;
    }

    public char[] reverse(char[] input){
        //todo: 이거 고치기
        for (int i=0; i<input.length/2; i++){
            char temp = input[i];
            input[i] = input[input.length -1 -i];
            input[input.length-1-i]=temp;
        }
        return input;
    }

    public void add(BigInteger big)
    {
        char[] result_array;
        int index = 0;
        if (fullInput.length>big.fullInput.length){
           result_array = add_small_to_big(fullInput, big.fullInput);
        }else{
            result_array= add_small_to_big(big.fullInput, fullInput);
        }

        String str_result = new String(result_array);
        //result array 의 앞은 이제 다 0임.
        for (int i=0; i<str_result.length(); i++){
            if (str_result.charAt(i) != Character.forDigit(0, 10)){
                index = i;
                break;
            }
        }

        str_result = str_result.substring(index);
        this.Input_string = str_result;
        //todo: return new 에서 big integer to string 이 이상해서 수정해야함.
    }

    //무조건 첫번째 수가 더 큰 add function
    public char[] add_small_to_big(char[] one, char[] two){
        //덧셈이 가질 수 있는 가장 긴 길이 = 101, 일단 모두 0으로 채우고, 뒤집은 뒤 앞의 0은 제거하기
        int radix = 10;
        char[] rev_result_array = new char[101];
        Arrays.fill(rev_result_array, Character.forDigit(0, radix));
        //여기까지는 array 가 0으로 잘 채워짐.
        char[] one_reversed = reverse(one);
        char[] two_reversed = reverse(two);
        int add;
        int add2;
        int temp1;
        int temp2;

        //하나씩 더하고 reverse array 에 저장하기
        for (int i=0; i<two.length; i++) {
            add = Integer.parseInt(String.valueOf(one_reversed[i])) + Integer.parseInt(String.valueOf(two_reversed[i]));
            if (add>=10){
                    rev_result_array[i+1] += 1;
                    add = add - 10;
            }
            //rev_result_array[i] += Character.forDigit(add, 10);
            //여기는 플러스 마이너스가 되어야함.
            temp1 = Integer.parseInt(String.valueOf(rev_result_array[i]));
            temp1 += add;
            if (temp1 > 10){
                temp1 -= 10;
                rev_result_array[i+1] += 1;
            }
            //rev_result_array[i] = (char)(temp1 +'0');
            rev_result_array[i] += temp1;
        }


        //길이가 다른 경우 보충
        for (int j=two.length; j<one.length; j++){
            add2 = Integer.parseInt(String.valueOf(rev_result_array[j])) + Integer.parseInt(String.valueOf(one_reversed[j]));
            System.out.println(add2);
            if (add2>=10){
                rev_result_array[j+1] += 1;
                add2 = add2-10;
            }
            //rev_result_array[j] += Character.forDigit(add2, 10);
            temp2 = Integer.parseInt(String.valueOf(rev_result_array[j]));
            temp2 += add2;
            if (temp2>20){
                System.out.println("bigger than 20");
            }
            if (temp2>10){
                temp2 -= 10;
                rev_result_array[j + 1] += 1;
            }
            rev_result_array[j] += temp2;
            System.out.println(rev_result_array[j]);
        }
        //todo: 10이 넘어가는 경우 반영 못함. (87+9)
        return reverse(rev_result_array);
    }

    public void subtract(BigInteger big)
    {
        //먼저 입력 받은 두 개의 수의 크기를 비교한다.
        //if biggerNumber is false, big is bigger.
        char[] result_array;
        int index = 0;
        boolean bigger = BiggerNumber(big);
        if (bigger){
            //number1 > number2
            result_array = subtract_small_from_big(fullInput, big.fullInput);
        }else{
            //number2 > number1
            result_array = subtract_small_from_big(big.fullInput, fullInput);
        }

        String str_result = new String(result_array);
        for (int i=0; i<str_result.length(); i++){
            if (str_result.charAt(i) != Character.forDigit(0, 10)){
                index = i;
                break;
            }
        }
        str_result = str_result.substring(index);
        System.out.println(str_result);
        this.Input_string = str_result;
    }

    public char[] subtract_small_from_big(char[] one, char[] two){
        char[] rev_result_array = new char[101];
        Arrays.fill(rev_result_array, Character.forDigit(0, 10));
        //여기까지는 array 가 0으로 잘 채워짐.
        char[] one_reversed = reverse(one);
        char[] two_reversed = reverse(two);
        //reverse 도 잘 됨.
        int sub;
        int sub2;
        int temp;
        int temp2;

        //하나씩 빼고 reverse array 에 저장하기
        for (int i=0; i<two.length; i++){
            sub = Integer.parseInt(String.valueOf(one_reversed[i])) - Integer.parseInt(String.valueOf(two_reversed[i]));
            if (sub<0){
                rev_result_array[i+1] -= 1;
                sub = sub+10;
            }
            //todo: 여기서 +=가 되어야함.
            rev_result_array[i] = Character.forDigit(sub, 10);
        }

        //길이가 다른 곳 보충
        for (int j=two.length; j<one.length; j++){
            sub2 =Integer.parseInt(String.valueOf(one_reversed[j]))- Integer.parseInt(String.valueOf(rev_result_array[j]));
            if (sub2<0){
                rev_result_array[j+1] -= 1;
                sub2 = sub2-10;
            }
            //rev_result_array[j] += Character.forDigit(add2, 10);
            temp2 = Integer.parseInt(String.valueOf(rev_result_array[j]));
            temp2 -= sub2;
            if (temp2<0){
                temp2 += 10;
                rev_result_array[j + 1] -= 1;
            }
            //todo: 여기도 += 가 되어야함.
            rev_result_array[j] = (char)(temp2 +'0');
        }

        //0이 아닌 곳을 먼저 찾아서 제거하기
        int index = 0;
        for (int i=0; i<rev_result_array.length; i++){
            if (rev_result_array[i] != 0){
                index = i;
                break;
            }
        }

        char[] cut_rev_array = new char[101];
        System.arraycopy(rev_result_array, index, cut_rev_array, index - index, rev_result_array.length - index);
        /*for (int i=index; i<rev_result_array.length; i++){
            cut_rev_array[i-index] = rev_result_array[i];
        }*/

        return reverse(cut_rev_array);
    }
  
    public BigInteger multiply(BigInteger number1)
    {
        //mulitply 의 최고 길이: 200자리
        char[] result_array;
        boolean bigger = BiggerNumber(number1);
        if (bigger){
            //number1 > number2
            result_array = multiply_small_to_big(fullInput, number1.fullInput);

        }else{
            //number2 > number1
            result_array = multiply_small_to_big(number1.fullInput, fullInput);
        }
        String str_result = new String(reverse(result_array));
        return new BigInteger(str_result);
    }

    public char[] multiply_small_to_big(char[] one, char[] two){
        char[] rev_result_array = new char[200];
        Arrays.fill(rev_result_array, Character.forDigit(0, 10));
        //one 이 two 보다 더 큰 수임.
        char[] one_reversed = reverse(one);
        char[] two_reversed = reverse(two);
        //int mult = 0;
        int temp=0;
        for (int i=0; i<two_reversed.length; i++){
            for (int j=0; j<one_reversed.length; j++) {
                int mult = Integer.parseInt(String.valueOf(two_reversed[i])) * Integer.parseInt(String.valueOf(one_reversed[j]));
                //mult 까지는 맞음
                System.out.println("mult");
                System.out.println(mult);
                temp += mult / 10;
                  //question: if I do this is 5/10 0 or 0.2?
            }
            //question: temp is always zero...
            System.out.println(temp);
            rev_result_array[i] = (char) (temp/10);
        }
        return rev_result_array;
    }

  
    @Override
    public String toString()
    {
        //여기서 toString 은 result array 을 string 으로 고쳐주는 역할임.
        //todo: result 의 string 을 출력하려면?
        return Input_string;
    }
  
    static BigInteger evaluate(String input) throws IllegalArgumentException {
        //todo: 모든 연산자의 경우..예를 들어 앞에 - 가 있는 곱셈 식의 경우.
        //evaluate 은 BigInteger 을 return 해주고, BigInteger.toString 이 최종 아웃풋이된다.
        String resultString;
        //아무것도 없을때는 + 라고 보면됨.
        //첫번째 숫자 연산자 (없으면 +)
        char firstNumberOperator = '+';
        //메인 연산자
        char mainOperator = '+';
        //연산자가 있는 위치 (그 뒤에 부터 두 번째 숫자로 지정하기)
        int main_index = 0;
        //두번째 숫자 연산자 (없으면 +)
        char secondNumberOperator = '+';
        int second_index = 0;
        String number1;
        String number2;
        //String str_result= "";

        input = input.replaceAll("\\s+", "");
        //입력값에서 화이트 스페이스 제거하기

        //처음에 나온 character 가 숫자가 아니라면 첫번째 연산자로 저장해주기
        if (!Character.isDigit(input.charAt(0))) {
            firstNumberOperator = input.charAt(0);
            input = input.substring(1);
            //input substring 잘 됨.
        }

        //이제 숫자부터 시작하는 input 을 탐색하여 first number string 잡고,
        //main operator, second number operator 을 저장하고
        //나머지 digit 들을 second number string 에 저장하기, 처음에 마이너스가 오는 경우
        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                mainOperator = input.charAt(i);
                main_index = i;
                break;
            }
        }
        //main operator, main_index 잘 됨.
        //second index, second number 자체가 저장이 안됨.

        if (!Character.isDigit(input.charAt(main_index + 1))) {
            secondNumberOperator = input.charAt(main_index + 1);
            second_index = main_index + 1;
        }

        //이제 input 내에서 main operator, second number operator 의 위치를 암.
        number1 = input.substring(0, main_index);
        //두번째 수의 operator 가 있는 경우
        if (second_index != 0) {
            number2 = input.substring(second_index + 1);
        } else {
            number2 = input.substring(main_index + 1);
        }


       //number2 저장까지 완벽!
        //number1, number2 string 을 캐릭터 어레이로 바꾸어주어야함.

        BigInteger number_one = new BigInteger(number1);
        BigInteger number_two = new BigInteger(number2);

        //연산의 경우 총 경우가 있을 수 있음
        //(없거나 +) (+) (+) --> 1 + 2 --> case1
        //(없거나 +) (+) (-) --> 1 - 2 --> case2
        //(없거나 +) (-) (+) --> 1 - 2 --> case2
        //(없거나 +) (-) (-) --> 1 + 2 --> case 1
        //(없거나 +) (*) (+) --> 1 * 2 --> case3
        //(없거나 +) (*) (-) --> -(1 * 2) --> case4

        //(-) (+) (+) --> 2-1 --> case 2
        //(-) (+) (-) --> - (1+2) --> case5
        //(-) (-) (+) --> - (1+2) --> case5
        //(-) (-) (-) --> 2-1 --> case2
        //(-) (*) (+) -->-(1*2) --> case4
        //(-) (*) (-) --> 1*2 --> case3
        //다시 마이너스 인 경우

        //case1: 1+2
        if(mainOperator == '+' && firstNumberOperator=='+' && secondNumberOperator=='+' ||
                mainOperator == '-' && firstNumberOperator=='+' && secondNumberOperator=='-'){
            number_one.add(number_two);
            //case2: 1-2
        }else if(mainOperator == '+' && firstNumberOperator=='+' && secondNumberOperator=='-' ||
                mainOperator == '-' && firstNumberOperator=='+' && secondNumberOperator=='+' ||
                mainOperator == '+' && firstNumberOperator=='-' && secondNumberOperator=='+' ||
                mainOperator == '-' && firstNumberOperator=='-' && secondNumberOperator=='-'){
            number_one.subtract(number_two);
            //case 3: 1 *2
        }else if(mainOperator == '*' && firstNumberOperator=='+' && secondNumberOperator=='+' ||
                mainOperator == '*' && firstNumberOperator=='-' && secondNumberOperator=='-'){
            number_one.multiply(number_two);
            //case 4: -(1*2)
        }else if(mainOperator == '*' && firstNumberOperator=='+' && secondNumberOperator=='-' ||
                mainOperator == '*' && firstNumberOperator=='-' && secondNumberOperator=='+'){
            //곱셈한거에 마이너스 붙여야함.
            number_one.multiply(number_two);
            //case5: -(1+2)
        }else if(mainOperator == '+' && firstNumberOperator=='-' && secondNumberOperator=='-' ||
                mainOperator == '-' && firstNumberOperator=='-' && secondNumberOperator=='+'){
            number_one.add(number_two);
            //더한 것에 마이너스 붙여야함.
        }

        resultString = number_one.Input_string;
        return new BigInteger(resultString);
    }


    public static void main(String[] args) throws Exception
    {
        try (InputStreamReader isr = new InputStreamReader(System.in))
        {
            try (BufferedReader reader = new BufferedReader(isr))
            {
                boolean done = false;
                while (!done)
                {
                    String input = reader.readLine();
  
                    try
                    {
                        done = processInput(input);
                    }
                    catch (IllegalArgumentException e)
                    {
                        System.err.println(MSG_INVALID_INPUT);
                    }
                }
            }
        }
    }
  
    static boolean processInput(String input) throws IllegalArgumentException
    {
        boolean quit = isQuitCmd(input);
  
        if (quit)
        {
            return true;
        }
        else
        {
            BigInteger result = evaluate(input);
            System.out.println(result);
  
            return false;
        }
    }
  
    static boolean isQuitCmd(String input)
    {
        return input.equalsIgnoreCase(QUIT_COMMAND);
    }
}
