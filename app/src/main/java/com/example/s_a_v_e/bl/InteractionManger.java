package com.example.s_a_v_e.bl;

import android.content.SharedPreferences;

import com.example.s_a_v_e.api.CmNotificationSubscriptionOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class InteractionManger {
  HashMap<String, Interaction> interactions = new HashMap<>();

  public void loadInteractions(SharedPreferences sharedPrefs){
    Set<String> storedInteractions =  sharedPrefs.getStringSet("SAVE_INTERACTIONS", new HashSet<String>());

    for(String interaction : storedInteractions){
      this.interactions.put(interaction, null);
    }
  }

  public CmNotificationSubscriptionOptions convertInteractions(){
    ArrayList<String> converted = new ArrayList<>();
    for(String interaction : this.interactions.keySet()){
      Interaction iInteraction = interactions.get(interaction);
      if(iInteraction.isEnded()){
        converted.add(iInteraction.getId());
      }
    }

    CmNotificationSubscriptionOptions cmr = new CmNotificationSubscriptionOptions();
    cmr.setContactPersonIds(converted.toArray(new String[converted.size()]));
    return cmr;
  }

  public void endInteraction(String id) {
    Interaction endedInteraction = interactions.get(id);
    endedInteraction.end(); // TODO: evaluate if exposure was long enough

    interactions.put(id, null);
  }

  public void startInteraction(String id){
    interactions.put(id, new Interaction(id));
  }
}
