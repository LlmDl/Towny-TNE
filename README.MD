# Towny-TNEMobs Control by LlmDl

## Overview
Towny-TNEMobs Control is a plugin made to bridge Towny and TNE's Mobs module.

This plugin brings awareness of Towny locations to TNE's Mobs module to allow for 2 features:

* The ability to penalize player's money rewards when not in the wilderness, and not in an exempted town.
  * The config's Exempted Towns list can be used to exempt towns from the penalty, useful for admins who want their spawn town's mob arena to not lessen money rewards.
  * The penalty's primary purpose is to encourage players to hunt mobs in the wilderness or in worlds that have had Towny-TNEMobs Control disabled, lessening the use of Mob Grinders in towns.

* The ability to limit the players' ability to 'use' their Currency Notes to only Towny Bank Plots.
  * This increases Role Playing, you must go to a bank plot to deposit/collect your virtual money from the physical Currency Notes.
  * It increases risk on PVP servers where someone could rob you of your undeposit/uncollected Currency Notes.

## Installation
Simply add the Towny-TNEMobs-Control.jar to your server's plugins folder.

After starting the first time, a new ```\TownyTNEMobsControl\config.yml``` will be created.

Read the config's comments and choose which worlds you might want to disable this plugin in, as well as which towns you might want to exempt from the penalty.

Set the ```Inside_Town_Multiplier``` to what you want, choose whether you want to limit the use of CurrencyNotes to only bank plots using the ```Deny_Currency_Note_Claiming_Outside_Bank_Plots``` option.

After saving the config use /townymobs reload to reload the config.

Read the below requirements to make sure you're using the required versions of Towny, TNE, and TNE's Mobs module.

## Requirements
Your server must be using the following versions or newer:

* Towny 0.94.0.2+
* TNE 1.1.8.25+
* TNE's Mobs module 0.1.3.0+ 

## Commands
* /townymobs - displays plugin version
* /townymobs reload - reloads the config.

## Credits
Obviously me, LlmDl, lead developer of Towny Advanced. As well as creatorfromhell, author of TheNewEconomy, who helped this make this plugin possible with the addition of key events to Mobs and TNE.
