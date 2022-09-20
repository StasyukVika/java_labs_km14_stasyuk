import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
    
    static public int add(String numbers)
    {
        int sum = 0;
        String delimiter = ",|\n", substring[], bb = "";
        int start_del = numbers.indexOf("//");
        int end_del = numbers.indexOf("\n");

        if (start_del != -1 )
        {
            delimiter = numbers.substring(start_del + 2, end_del);
            numbers = numbers.substring(end_del + 1);
            if (delimiter.endsWith("]") && delimiter.indexOf("[") == 0) {
                Pattern pattern = Pattern.compile("\\[(.*?)\\]");
                Matcher matcher = pattern.matcher(delimiter);

                int groupCount = matcher.groupCount();
                while (matcher.find()) {
                    for (int i = 0; i <= groupCount; i++) {
                        String m = matcher.group(i);
                        numbers = numbers.replaceAll(Pattern.quote(m), ",");
                    }
                }
                delimiter = ",";
                substring = numbers.split(Pattern.quote(delimiter));
            }
        } 
        substring = numbers.split(delimiter);

        int num_arr[] = new int[substring.length];
        for (int i = 0; i < substring.length; i++) 
        {
            num_arr[i] = Integer.parseInt(substring[i]);
        }
    
        try{
            for (int i = 0; i < substring.length; i++)
           {
                if (num_arr[i] > 1000)
               {
                   sum = sum + 0;
                } else if (num_arr[i] < 0)
                {
                     bb = bb + " " + String.valueOf(num_arr[i]);
                
                } else
                {
                    sum = sum + num_arr[i];
                }
            }
            if(bb != "") 
            {
                throw new Exception("Negative numbers are not allowed");
            }
        
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            System.out.println("List of negative numbers:" + bb);
        }
        return sum;
    }
    
    public static void main(String[] args) 
    {
        String numbers = "//;\n1;7;1";
        int n = add(numbers);
        System.out.println("Result: " + n );
    }
}
