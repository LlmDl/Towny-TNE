package com.gmail.llmdlio.townytne.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.configuration.InvalidConfigurationException;

import com.gmail.llmdlio.townytne.TownyTNE;
import com.gmail.llmdlio.townytne.config.CommentedYamlConfig;

public class TownyTNEConfig {

    private TownyTNE plugin;
    private CommentedYamlConfig config;
    private String newline = System.getProperty("line.separator");
    
    public TownyTNEConfig(TownyTNE plugin){
        this.plugin = plugin;
    }

    public void reload(){
        loadConfig();
    }

    // Method to load TownyTNEMobsBridge\config.yml
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
        addDefault("Version", ""); 

        addComment("Disabled_Worlds", newline, 
                "  # A list of worlds which will not be affected by this plugin.",
                "  # Any world in which Towny has usingTowny=false will already be disabled automatically.",
                "  # Disabled_Worlds:",
                "  # - world",
                "  # - world_nether");
        addDefault("Disabled_Worlds", new ArrayList<String>());

        addComment("Exempted_Towns", newline, 
                "  # A list of towns in which players are not penalized for killing things with lesser money drops.",
                "  # Exempted_Towns:",
                "  # - SpawnTown",
                "  # - Best_Town_on_Dah_Server");
        addDefault("Exempted_Towns", new ArrayList<String>());

        addComment("Inside_Town_Multiplier", newline,
                "  # Factor used to reduce or increase drops inside non-exempted towns.",
                "  # Example: Set to .8 to cause money drops to be 80% of original amount.",
                "  #          0.8 x $10.00 = $8.00",
                "  # Set to 1.0 to disable this feature.");
        addDefault("Inside_Town_Multiplier", 0.8);

        addComment("Deny_Currency_Note_Claiming_Outside_Bank_Plots", newline,
                "  # When set to true TNE Currency Notes will only be usable while inside Towny Bank plots.");
        addDefault("Deny_Currency_Note_Claiming_Outside_Bank_Plots", false);
        
        addComment("Enforce_Shop_Plots", newline, 
                "  # Restrict TNE shops to Towny shop plots.");
        addDefault("Enforce_Shop_Plots", false);
        
        addComment("Require_Shop_Plot_Ownership", newline,
                "  # When Enforce_Shop_Plots is true, do we require the shop-maker to personally own the Towny shop plot.",
                "  # If true, they must own it. If false, they must be able to build at the shop location.",
                "  # Set to false if you want to have shared/group shop plots.");
        addDefault("Require_Shop_Plot_Ownership", true);

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
    	if (path.equals("Version"))
    		config.set(path, plugin.getDescription().getVersion());
    	else if (!hasPath(path))
            config.set(path, defaultValue);
    }
}
