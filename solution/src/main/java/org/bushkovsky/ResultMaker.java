package org.bushkovsky;

import org.bushkovsky.services.Service;

import java.util.HashMap;
import java.util.Map;

public class ResultMaker {
    Integer[] values;

    String[] names;

    public ResultMaker(){
        values = new Integer[]{0, 0, 0};
        names = new String[]{"", "", ""};
    }


    public String getRank(Service service){
        String[] result = ranking(service);
        return result[0] + "\n" + result[1] + "\n" + result[2];
    }

    private String[] ranking(Service service){
        HashMap<String, Integer> contributorsRating = service.getContributorsRating();
        for (Map.Entry<String, Integer> entry : contributorsRating.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            for (int i = 0; i < 3; i++){
                if(maximum(value, values[i])){
                    values[i] = value;
                    names[i] = key;
                    break;
                }
            }

        }
        return names;
    }

    public boolean maximum(int a, int b){
        return a > b;
    }
}
