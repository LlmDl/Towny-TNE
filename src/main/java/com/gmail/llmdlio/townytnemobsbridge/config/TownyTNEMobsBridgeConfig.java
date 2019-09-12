package com.gmail.llmdlio.townytnemobsbridge.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.configuration.InvalidConfigurationException;

import com.gmail.llmdlio.townytnemobsbridge.TownyTNEMobsBridge;
import com.gmail.llmdlio.townytnemobsbridge.config.CommentedYamlConfig;

public class TownyTNEMobsBridgeConfig {
    
    private TownyTNEMobsBridge plugin;
    private CommentedYamlConfig config;
    private String newline = System.getProperty("line.separator");
     
    public TownyTNEMobsBridgeConfig(TownyTNEMobsBridge plugin){
        this.plugin = plugin;
    }
    
    public void reload(){
        loadConfig();
    }
    
    // Method to load UndeadRiders\config.yml
    private void loadConfig(){ 
        File f = new File(plugin.getDataFolder(), "config.yml"); 
         
        if(!f.exists()) { 
            try { 
                f.createNewFile(); 
            } catch (IOException e) { 
                e.printStackTrace(); 
            } 
        } 
         
        config = new CommentedYamlConfig();        

        try { 
            config.load(f); 
        } catch (FileNotFoundException e) { 
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } catch (InvalidConfigurationException e) { 
            e.printStackTrace(); 
        } 

        addComment("Version","  # Towny-TNEMobs-Bridge by LlmDl."); 

        addDefault("Version", plugin.getDescription().getVersion()); 

        addComment("Disabled_Worlds", newline, 
                "  # A list of worlds where players are not penalized for killing things with lesser money drops.",
                "  # Disabled Worlds:",
                "  #   - world",
                "  #   - world_nether");
        addDefault("Disabled_Worlds", new ArrayList<String>());

        addComment("Exempted_Towns", newline, 
                "  # A list of towns in which players are not penalized for killing things with lesser money drops.",
                "  # Exempted Towns:",
                "  #   - SpawnTown",
                "  #   - Best_Town_on_Dah_Server");
        addDefault("Exempted_Towns", new ArrayList<String>());

        addComment("Inside_Town_Multiplier", newline,
                "  # Factor used to reduce or increase drops inside non-exempted towns.",
                "  # Example: Set to .8 to cause money drops to be 80% of original amount.",
                "  #          0.8 x $10.00 = $8.00");
        addDefault("Inside_Town_Multiplier", 0.8);

        // Write back config 
        try { 
            config.save(f); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
    } 
    
    public CommentedYamlConfig getConfig() { 
        return config;
    }
    
    private boolean hasPath(String path) {
        return config.isSet(path);
    }
    
    private void addComment(String path, String... comment) {
            config.addComment(path, comment);       
    }
    
    private void addDefault(String path, Object defaultValue) {
        if (!hasPath(path))
            config.set(path, defaultValue);     
    }
}    
