package index;
import utils.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class ReverseIndex implements Index{
    private HashMap<String, PostingList> dictionary = new HashMap<>();
    private int documentNumber;
    public ReverseIndex(){ documentNumber = 0;}

    @Override
    public void populateIndex(Document document) throws NotValidDocumentException{

        HashMap<String, Integer> wordbag = generateWordbag(document.getFilename());
        Set<String> words = wordbag.keySet();
        PostingList list;

        for(String word : words){
            if(dictionary.get(word) == null){
                list = new PostingList();
            }else{
                list = dictionary.get(word);
            }

            list.add(document, wordbag.get(word));
            dictionary.put(word, list);
        }

        documentNumber = documentNumber + 1;
    }

    private HashMap<String, Integer> generateWordbag(String filename) throws NotValidDocumentException {

        TextUtils filter = TextUtils.getInstance();
        File file = new File(filename);
        Scanner scanner;

        try{
            scanner = new Scanner(file);
        }catch(FileNotFoundException e){
            throw  new NotValidDocumentException("Document not found");
        }

        scanner.useDelimiter(" ");

        HashMap<String, Integer> content = new HashMap<String, Integer>();
        String[] buffer;

        while(scanner.hasNext()){
            buffer = filter.textFilter(scanner.next());
            for(String word : buffer){
                if(word.length() > 1){
                    if(content.get(word) == null){
                        content.put(word, 1);
                    }else{
                        content.put(word, content.get(word) + 1);
                    }
                }
            }
        }

        return content;
    }

    public PostingList get(String word){
        return dictionary.get(word);
    }

    public int getDocumentNumber(){
        return documentNumber;
    }

    @Override
    public String toString() {
        String result = "[\n";
        for(String str : dictionary.keySet()){
            result += str + " : " + dictionary.get(str).toString() + "\n";
        }

        result += "]";
        return result;
    }
}
