import java.io.*;
import java.net.*;
import java.awt.*;



public class SaveImageFromUrl {

	public static void main(String[] args) throws Exception {
        int numProcessed = 0;
        int imagesSaved = 0;
        int InvalidURLs= 0;
        if(args.length != 1)    //Check for input file
        {
            System.out.println();
            System.err.println("********** Input file not specified **********");     //print error if input file not found
            System.err.println("   Format: java SaveImageFromUrl <filename>"); //print format
            System.out.println();
            return;
        }
        try     //try read file
        {
            
            /////// *********** ////////
            //File dir = new File("nameoffolder");
            //dir.mkdir();
            //String ext = FilenameUtils.getExtension("/path/to/file/foo.txt");
            /////// *********** ////////
            
            //String noHTMLString = htmlString.replaceAll("\\<.*?>",""); To remove htlm tags
            
            
            BufferedReader br = new BufferedReader(new FileReader(args[0]));    //new buffered reader
            String s=br.readLine(); //String s equals buffered reader line read
            
            while(s!=null)  //While lines still read
            {
                
                //Check if url is that of immage
                boolean valid = isValidImage(s);
                if (valid == true){
                    String imageUrl = s;
                    String destinationFile = fileName(s) + ".jpg";

                    saveImage(imageUrl, destinationFile);
                    System.out.println("URL: " + imageUrl + " File saved as: " + destinationFile);
                    imagesSaved++;
                }
                else{
                    //Save new text file with invalid url's
                    infoSave(s, "Invalid URL.txt");
                    InvalidURLs++;
                }
                
                
                
                //infoSave(s, "Processed URL's.txt");
                
                //getHTML(imageUrl); //Get HTML from url and print and save to file
                
                s=br.readLine();    //Update string read
                numProcessed++;
            }
            br.close();
            
        }
        catch(Exception e)  //read file catch
        {
            System.out.println();
            System.out.println("********** Error trying to save with file '" + args[0] + "' **********");   //Print Invalid File expection
            System.out.println();
             e.printStackTrace(System.out);
        }
        
        System.out.println();
        System.out.println("********** Done " + numProcessed + " Items Processed **********");
        System.out.println();
        System.out.println("Images Saved: " +"\t"+ imagesSaved);
        System.out.println("Invalid URL's: " +"\t"+ InvalidURLs);
        System.out.println();
		
	}
    
    
    
    
    /**
     * @desc Gets the filename from a url
     * @args String - File URL
     * @return String - Filename from url
     */
    public static String fileName(String url) {
        
        String fileName = url.substring( url.lastIndexOf('/')+1, url.length() );
        String fileNameWithoutExtn = fileName.substring(0, fileName.lastIndexOf('.'));
        
        return fileNameWithoutExtn;
    }
    
    
    /**
     * @desc Saves an image from a given URL
     * @args String - Image URL
     * @args String - Filename for the saved image
     * @output File (IMAGE) - The Image from given URL
     */
	public static void saveImage(String imageUrl, String destinationFile)  { //Save Image
        try{
            URL url = new URL(imageUrl);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(destinationFile);
            byte[] b = new byte[2048];
            int length;

                while ((length = is.read(b)) != -1) {
                    os.write(b, 0, length);
                }

            is.close();
            os.close();
        }
        catch(Exception e){ //If image cannot be downloaded open the URL
            try {
                java.awt.Desktop.getDesktop().browse(java.net.URI.create(imageUrl));
            }
            catch (Exception f) {
                f.printStackTrace();
            }
        }
	}
    
    
    /**
     * @desc Checks if string is an email address
     * @args String - Potential email
     * @return boolean - true | false
     */
    public boolean isValidEmailAddress(String email) {
        
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        
        return m.matches();
    }
    
    /**
     * @desc Checks if URL string is an image
     * @args String - Potential image URL
     * @return boolean - true | false
     */
    public static boolean isValidImage(String url){
        
        String urlPattern = "(http(s?):/)(/[^/]+)+" + "\\.(?:jpg|gif|png)";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(urlPattern);
        java.util.regex.Matcher m = p.matcher(url);
        
        return m.matches();
    }
    
    
    

    
    /**
     * @desc Saves HTML file from given URL
     * @args String - Website URL
     * @output Console - The given URL's HTML
     * @output File (.html) - The given URL's HTML
     */
    public static void getHTML(String url) {
        
        try {
            // get URL content
            URL address = new URL(url);
            URLConnection connection = address.openConnection();
            
            // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            
            String inputLine;
            
            url = "test.html";
            
            //save to this filename
            String fileName = (url);
            File file = new File(fileName);
            
            if (!file.exists()) {
                file.createNewFile();
            }
            
            
            //use FileWriter to write file
            FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
            BufferedWriter bw = new BufferedWriter(fw);
            
            while ((inputLine = br.readLine()) != null) {
                System.out.println(inputLine);
                bw.write(inputLine);
            }
            
            bw.close();
            br.close();
            
            System.out.println("Done");
            
        }
        catch (MalformedURLException e) {
            
        }
        catch (IOException e) {
           
        }
        
    }
    
    /**
     * @desc Saves a text file of sent information
     * @desc Appends file if it already exits
     * @args String - String to be writen to file
     * @args String - Name of file to be saved
     * @output File (.txt) - A collection Strings to be save to specified file
     */
    public static void infoSave(String lineContent, String fileName){
        // open the stream and put it into BufferedReader
        try{
            String inputLine = lineContent;
            
            //save to this filename
            //String fileName = "Invalid URLs.txt";
            File file = new File(fileName);
            
            if (!file.exists()) {
                file.createNewFile();
            }
            
            //use FileWriter to write file
            FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write(inputLine);
            bw.newLine();
            
            bw.close();
        }
        catch (IOException e) {
            
        }
        
    }

}
