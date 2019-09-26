# Towny-TNE by LlmDl

## Overview
### Towny-TNE is a plugin made to bridge Towny and The New Economy.

#### This plugin brings awareness of Towny locations to TNE's Mobs module and Currency Notes to allow for 2 features:

* The ability to penalize player's money rewards when not in the wilderness, and not in an exempted town.
  * The config's Exempted Towns list can be used to exempt towns from the penalty, useful for admins who want their spawn town's mob arena to not lessen money rewards.
  * The penalty's primary purpose is to encourage players to hunt mobs in the wilderness or in worlds that have had Towny-TNE disabled, lessening the use of Mob Grinders in towns.

* The ability to limit the players' ability to 'use' their Currency Notes to only Towny Bank Plots.
  * This increases Role Playing, you must go to a bank plot to deposit/collect your virtual money from the physical Currency Notes.
  * It increases risk on PVP servers where someone could rob you of your undeposit/uncollected Currency Notes.

### This plugin also has the ability to interact with TNE's Signs module to allow the following:

* The ability to limit creation of TNE sign shops to Towny shop plots.
  * You can optionally limit the creation of shops to only shop plots owned by the player, or
  * You can limit it to shop plots where the creator is able to also build (allowing for group-owned shop plots.)

## Installation
Simply add the Towny-TNE.jar to your server's plugins folder.

After starting the first time, a new ```\Towny-TNE\config.yml``` will be created.

Read the config's comments and choose which worlds you might want to disable this plugin in, as well as which towns you might want to exempt from the penalty.

Set the ```Inside_Town_Multiplier``` to what you want, choose whether you want to limit the use of CurrencyNotes to only bank plots using the ```Deny_Currency_Note_Claiming_Outside_Bank_Plots``` option.

Set the ```Enforce_Shop_Plots``` to true or false if you'd like to enable the limitation of sign shops to shop plots.

Set the ```Require_Shop_Plot_Ownership``` to true if you'd like players to only be able to make sign shops in shop plots they personally own. 

After saving the config use /towny-tne reload to reload the config.

__Read the below requirements to make sure you're using the required versions of Towny, TNE, and TNE's Mobs module.__

## Upgrading
Towny-TNE uses the commentedconfiguration developed by dumptruckman, so you won't have to worry about your config when updating Towny-TNE.

New config values will be added with default settings and your old settings will be left intact.

## Requirements
Your server __must__ be using the following versions or newer:

* Towny 0.94.0.11 (Which should only be used on MC 1.14.* or newer.)
* TNE 0.1.1.9
* TNE's Mobs module 0.1.3.0 
* TNE's Signs module 0.0.1.3

## Commands
* /towny-tne - displays plugin version
* /towny-tne reload - reloads the config. 

## Permissions
* townytne.version - allows use of ```/towny-tne```, defaults to ops.
* townytne.reload - allows use of ```/towny-tne reload```, defaults to ops.

## Credits
Obviously me, LlmDl, lead developer of Towny Advanced. As well as creatorfromhell, author of TheNewEconomy, who helped this make this plugin possible with the addition of key events to Mobs and TNE. Also worth mentioning is dumptruckman, who is responsible for the code used to make the config.

## Changelog:
v1.0:  

  - Initial version.
  - Currency notes can be limited to Bank plots.
  - Currency drops can be altered inside towns, while exempting a list of towns.
  - Currency notes and drops manipulation can be disabled in a list of worlds.

v1.1:

  - Item Sign shop creation can be limited to just Shop plots.
    - Optionally it can be a personally owned shop plot, or any shop plot where the creator can build.
  - Made disabledWorlds tests also ignore worlds where usingTowny is false.
