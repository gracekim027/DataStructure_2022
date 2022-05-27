import java.io.*;
import java.lang.reflect.Array;
import java.util.Stack;


public class CalculatorTest
{

	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true)
		{
			try
			{
				String input = br.readLine();

				if (input.compareTo("q") == 0)
					break;
				convertPostfix(input);
			}
			catch (Exception e)
			{
				//System.out.println("입력이 잘못되었습니다. 오류 : " + e);
				System.out.println("ERROR");
			}
		}
	}

	public static StringBuilder addPad(String input){
		return new StringBuilder(input).append(" ");
	}

	public static boolean isOperator(char operator){
		return operator == '^' || operator == '*' || operator == '%' || operator == '+' || operator == '/';
	}

	//function that converts infix into postfix string
	public static void convertPostfix(String input) {
		boolean flag = true;
		//flag checks if the error has been printed yet or not.
		Stack<String> operatorStack = new Stack<>();
		String checkError = input.replaceAll("\\s+", "");

		if (isOperator(checkError.charAt(0))){
			flag = false;
		}

		//add a space between all operators and operands
		StringBuilder sb = new StringBuilder();
		sb.append(input.charAt(0));

		for (int i = 1; i < input.length(); i++) {
			if (Character.isDigit(input.charAt(i)) && Character.isDigit(input.charAt(i - 1))) {
				sb.append(input.charAt(i));
			} else {
				sb.append(" ").append(input.charAt(i));
			}
		}

		//make sure there is only one space between operand and operators
		String inputFixed = sb.toString().replaceAll("  +", " ");
		String[] inputArray = inputFixed.split("\\s+");

		int numCount1 = 0;
		int opCount1=0;
		int numLeft = 0;
		int numRight = 0;

		//looping through the string, if the char is a digit add it to string
		//if the char is an operator, add it to the stack
		StringBuilder output = new StringBuilder();

		//change - into unary ~
		if(flag){
			for (int i=0; i< inputArray.length; i++){
				if(inputArray[i].charAt(0) == '-'){
					if (i > 0) {
						String prev = inputArray[i-1];
						if ((!isNumeric(prev) && !isParenthesis(prev))||
								prev.charAt(0) == '(') {
							Array.set(inputArray, i, "~");
							inputArray[i] = "~";
							opCount1 += 1;
						}
					}else {
						Array.set(inputArray, i, "~");
						inputArray[i] = "~";
						opCount1 += 1;
					}
				}
			}
		}

		if (flag){
			for (int i = 0; i < inputArray.length; i++) {
				String current = inputArray[i];

				if (isNumeric(inputArray[i])) {
					//when the char is digit, add it to the output string
					StringBuilder element = addPad(inputArray[i]);
					output.append(element);
					numCount1 += 1;
				}

				//two operators, two operands, operator without operand --> error.
				if (i>0){
					String prev = inputArray[i-1];
					if (isOperator(current.charAt(0)) && isOperator(prev.charAt(0)) ||
							isNumeric(current) && isNumeric(prev) ||
					isOperator(prev.charAt(0)) && current.charAt(0) == ')'){
						flag = false;
					}
				}

                //stack the binary operators
				if (!isParenthesis(current) && !isNumeric(current)) {
					opCount1 += 1;

					if (operatorStack.isEmpty()) {
						operatorStack.push(current);
					} else {

						//if the stack is not empty, compare the priority of the topNode in stack
						String topNode = operatorStack.peek();
						int curr = returnPriority(current.charAt(0));
						int top = returnPriority(topNode.charAt(0));

						if (curr == 1 && top == 1){
							operatorStack.push(current);
						} else {
							boolean compare = true;
							while (compare){
								if (top <= curr){
									String poppedNode = operatorStack.pop();
									StringBuilder element = addPad(poppedNode);
									output.append(element);
									if (!operatorStack.isEmpty()){
										topNode = operatorStack.peek();
										top = returnPriority(topNode.charAt(0));
									}else{
										operatorStack.push(current);
										compare = false;
									}
								}else{
									operatorStack.push(current);
									compare = false;
								}
							}
						}

					}

					//parenthesis
				}else if (isParenthesis(current)) {
					if (current.charAt(0) == '(') {
						//if it's left, push in stack
						operatorStack.push(current);
						numLeft += 1;
						
					} else if (current.charAt(0) == ')') {
						//if it's right, pop the elements in stack until we get left
						numRight += 1;
						boolean leftNotOut = true;
						String top = operatorStack.peek();
						while (leftNotOut){
							if (top.charAt(0) != '('){
								StringBuilder element = addPad(top);
								output.append(element);
								operatorStack.pop();
								if (!operatorStack.isEmpty()){
									top = operatorStack.peek();
								}else{
									leftNotOut = false;
								}
							}else {
								operatorStack.pop();
								leftNotOut = false;
							}
						}
					}

					if (numRight > numLeft){
						flag = false;
					}
				}
			}
	}

		while (!operatorStack.isEmpty()){
			//pop the rest elements into the output string
			String stackElement = operatorStack.pop();
			StringBuilder element = addPad(stackElement);
			output.append(element);
		}


		String outputFixed = output.toString().trim().replaceAll("  +", " ");

		if (numCount1 == 0 || opCount1 == 0 || (numRight != numLeft)){
			flag = false;
		}

		if (flag) {
			calculate(outputFixed);
		}else{
			throw new printError();
		}
	}

	public static boolean isParenthesis(String str){
		return str.charAt(0) == '(' || str.charAt(0) == ')';
	}

	public static int returnPriority(char token){
		if (token == '~' || token == '^') return 1;
		if (token == '*' || token == '/' || token == '%') return 2;
		if (token == '+' || token == '-') return 3;
		if (token == '(' || token == ')') return 4;
		else throw new printError();
	}

	public static void calculate(String fixedOutput){
		String[] postfix = fixedOutput.split("\\s+");

		long result = 0;
		boolean flag = true;

		//save the digits in long structure
		Stack<Long> numberStack = new Stack<>();

		for (String token : postfix) {
			if (isNumeric(token)) {
				//if the char is a digit, push it into the numberStack
				long tokenLong = Long.parseLong(token);
				numberStack.push(tokenLong);
			} else {
				//We have to calculate with the numbers in stack, and push the result back in

				if (token.equals("~")) {
					//unary -
					long firstNum = numberStack.pop();
					firstNum = -firstNum;
					numberStack.push(firstNum);

				} else if (numberStack.size() > 1) {
					long secondNum = numberStack.pop();
					long firstNum = numberStack.pop();

					switch (token) {
						case "-":
							//subtract
							firstNum -= secondNum;
							numberStack.push(firstNum);
							break;

						case "+":
							//addition
							firstNum += secondNum;
							numberStack.push(firstNum);
							break;

						case "*":
							//multiplication
							firstNum *= secondNum;
							numberStack.push(firstNum);
							break;

						case "/":
							//divide
							if (secondNum != 0) {
								firstNum = firstNum / secondNum;
								numberStack.push(firstNum);
							} else {
								flag = false;
							}
							break;

						case "^":
							//power
							if (firstNum == 0 && secondNum < 0) {
								flag = false;
							} else {
								firstNum = (long) Math.pow(firstNum, secondNum);
								numberStack.push(firstNum);
							}
							break;

						case "%":
							//modulo
							if (secondNum != 0) {
								firstNum = firstNum % secondNum;
								numberStack.push(firstNum);
							} else {
								flag = false;
							}
							break;
					}
				}
			}

		}
		//pop the result into the result string
		if (!numberStack.isEmpty()){
			result = numberStack.pop();
		}

		if (flag) {
			System.out.println(fixedOutput);
			System.out.println(result);
		}else{
			throw new printError();
		}
	}

	public static boolean isNumeric(String str) {
		try {
			Long.parseLong(str);
			return true;
		} catch(NumberFormatException e){
			return false;
		}
	}
}

class printError extends RuntimeException{
	public printError() {
	}
}
