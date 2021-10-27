# Decodex User Guide <!-- omit in toc -->

## Introduction

Decodex is a **Command Line Interface (CLI) application for Capture-The-Flag (CTF) players to quickly transform data from one format to another with extreme ease**. The intuitive interaction can help speed up a player's performance during CTFs and save time without having to manually code the tedious data transformations.

This guides serves to help you understand the usage of the program for quick and easy data manipulation.

> :information_source: This user guide is tailored for CTF players who have basic understanding of information security concepts.

![carbon(6).png](images/carbon(6).png)

## Table of Contents <!-- omit in toc -->
- [Quick Start](#quick-start)
- [Terminologies](#terminologies)
- [List of Available Modules](#list-of-available-modules)
- [Features](#features)
  - [Understanding the Application's Prompt](#understanding-the-application-prompt)
  - [Basic Commands](#basic-commands)
    - [Input of data: `input`](#input-of-data-input)
    - [List available modules: `list`](#list-available-modules-list)
    - [Selection of module: `select`](#selection-of-module-select)
    - [Resetting of data: `reset`](#resetting-of-data-reset)
    - [Exiting the program: `exit`](#exiting-the-program-exit)
  - [Recipe Commands](#recipe-commands)
    - [Create new recipe: `recipe`](#create-new-recipe)
    - [Select recipe for editing: `recipe select`](#selecting-recipe-for-editing)
    - [List modules in recipe: `recipe list`](#list-modules-in-recipe)
    - [Add module to recipe: `recipe push`](#add-module-to-recipe)
    - [Remove module from recipe: `recipe pop`](#remove-module-from-recipe)
    - [Clear all modules in recipe: `recipe reset`](#clear-all-modules-in-recipe)
    - [Delete recipe: `recipe delete`](#delete-recipe)
  - [Saving Recipes to file `[coming in v2.0]`]()
  - [Reading/writing data from/to file `[coming in v2.0]`]()
- [Command Summary](#command-summary)
- [FAQ]()

## Terminologies

This section serves to help the user better understand the terminologies used in this user guide.

| Terminology                 | Definition                                                                                                                                                    |
|-----------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Application, Program        | Refers to the Decodex program. This two terms are used interchangeably in this User Guide.                                                                    |
| Encoding                    | Convert a message into a coded form.                                                                                                                          |
| Decoding                    | Convert a coded message into an intelligible form.                                                                                                            |
| Base64, Binary, Hexadecimal | Common types of data encoding standards.                                                                                                                      |
| Console                     | This refers to your command prompt window.                                                                                                                    |
| Argument                    | The additional information you provide to the program's command.                                                                                              |
| Module                      | A self-contained set of instructions to process your data into another form.                                                                                  |
| Recipe                      | Acts as a container for you to select your modules. When multiple modules are selected, this forms a "module chain". By default, you do not have any recipes. |
| 💡                           | Represents a good tip for you.                                                                                                                                |
| ❗                           | Represents something important that you should take note of.                                                                                                  |
| ℹ                           | Represents additional information regarding commands/features for you to better understand how to use it.                                                     |
| 🎮                           | Represents something optional that you can try out, mostly for exploring or fun.                                                                              |

## Quick Start
1. Ensure you have Java version 11 or above installed on your computer.
   1. If you haven't, you may download it [here](https://www.oracle.com/java/technologies/downloads/#java11-linux)
   > ❗ You should download the installation relative to your Operating System.
2. Next, download the latest `decodex.jar` [here](https://github.com/AY2122S1-CS2113T-T10-3/tp/releases)
   1. Simply click on the `decodex.jar` under "Assets" and the download should start.
3. After downloading, you can open up `command prompt`.
    > 💡To open command prompt, press `win + r` at the same time, then type and enter `cmd`.
4. Then, you can now run the `decodex.jar` by typing in `java -jar decodex.jar`  and the Decodex's prompt should appear. Also, please ensure that you are at the directory where `decodex.jar` is.
   1. In the screenshot below, the `decodex.jar` is located in the `Downloads` folder.
   ![carbon(23).png](images/carbon(23).png)
5. Here on, you can try out some of the basic commands below:
   1. `input I am groot`: Inputs into the program the data ("I am groot").
   2. `list`: Lists all available modules and recipes that you can use.
   3. `select module base64encode`: Selects and runs the base64-encoding module on the data.
   4. `reset`: Resets the changes made to data - resetting to its original data.
   5. `exit`: Exits the application.
    > 🎮 Side-Quest! You may try to find out what `NTEgMzEgNTIgNDcgNjUgMzAgNGUgNTQgNGQgNmEgNDUgNzggNGQgMzEgNTIgMzk=` is using our application! If you are new here, you can continue reading this guide to understand how to unravel this mysterious text!
6. For more information on Decodex's features, please refer to the Features Section.

## List of Available Modules
1. Base64
   1. `base64encode` - Encodes the data using base64 format
   2. `base64decode` - Decodes the data using base64 format
2. Hexadecimal
   1. `hexencode` - Converts the input string to hexadecimal bytes
   2. `hexdecode` - Converts a hexadecimal string back into its raw value
3. Binary
   1. `binencode` - Encodes the data using binary format
   2. `bindecode` - Decodes the data using binary format
4. Rotational Cipher
   1. `rotencode` - Rotates alphabetical characters by a specified integer offset

> :information_source: These are some of the more common encoding/decoding/cipher methods that can be found in CTF competitions.

## Features

> :information_source: Arguments enclosed in `<>` are **mandatory** arguments while arguments enclosed in `{}` are **optional** arguments. For example, `select <moduleName> {moduleArgument}` would mean that `moduleName` is mandatory while `moduleArgument` is optional.

> :information_source: Arguments must be entered in the **exact order and position** as specified in the commands' respective formats.

> :information_source: All commands and arguments are **case-sensitive**.

### Understanding the Application's Prompt

![carbon(17).png](images/carbon(17).png)

After running the program, it would display a prompt showing the name of the program `Decodex` , followed by the name of the recipe that is "currently being edited" (if any) in `[]`. This currently edited recipe will be the target for some `recipe` commands such as `recipe push`, `recipe pop` and `recipe reset`.

### Basic Commands

#### Input of data: `input`

Stores the data to be processed by modules.

Format: `input <data>`

> :information_source: `data` will be treated as text.

Examples:

- `input SGVsbG9Xb3JsZA==` Enter base64-encoded data.
- <code>input  hello world </code> Enter plain text as data.

![carbon(1).png](images/carbon(1).png)

> :exclamation: Note that the program will consider all characters as `data` following the first space character in the input command. Observe the leading and trailing whitespaces in the result of the second command in the above screenshot.

> :exclamation: Non-printable characters (e.g. null characters) may not be correctly displayed and could potentially scramble the user interface.

####List available modules or recipes: `list`

Shows a list of all available modules.

Format: `list`

![carbon(8).png](images/carbon(8).png)

#### Selection of module or recipe: `select`

Selects a module and processes the data accordingly. Subsequent selection of modules will process the transformed data output from the previous module.

Format: `select <moduleName>`

`moduleName` is the name of an available module supported by the program.

Examples:

- `select base64decode` Decode the base64 encoded data.
- `select hexencode` Encode the data into hexadecimal format.

![carbon(9).png](images/carbon(9).png)

> :exclamation: Non-printable characters (e.g. null characters) may not be correctly displayed and could potentially scramble the user interface.

#### Resetting data: `reset`

Resets the transformed data back to the original input.

Format: `reset`

#### Exiting the program: `exit`

Exit the program.

Format: `exit`

## Recipe Commands: `recipe`

### Create a New Recipe: `recipe new`

Creates a new recipe and sets it as currently being edited.

Format: `recipe new <recipeName>`

Example:

- `recipe new testRecipe` Creates a new recipe with the name `testRecipe`.

![recipeNew.png](images/recipeNew.png)

### Select a Recipe for Editing: `recipe select`

Sets a recipe as currently being edited.

Format: `recipe select <recipeName>`

Example:

- `recipe select testRecipe` Selects `testRecipe` as the recipe to be edited.

![recipeSelect.png](images/recipeSelect.png)

### List Modules in a Recipe: `recipe list`

Show all the modules in a recipe, in the order they were added.

Format: `recipe list <recipeName>`

Example:

- `recipe list testRecipe` Lists the modules in the recipe named `testRecipe`.

![recipeList.png](images/recipeList.png)

### Add a Module into a Recipe: `recipe push`

Adds a module into the recipe that is currently being edited.

Format: `recipe push <moduleName> {moduleArgument}`

Example:

- `recipe push base64encode` Adds the `base64encode` module into the current editing recipe.
- `recipe push rotencode 13` Adds the `rotencode` module with a single argument `13` into the current editing recipe.

![recipePush.png](images/recipePush.png)

### Remove a Module from a Recipe: `recipe pop`

Removes the most recently added module from the recipe that is currently being edited.

Format: `recipe pop`

![recipePop.png](images/recipePop.png)

### Clear All Modules in a Recipe: `recipe reset`

Removes all modules from the recipe that is currently being edited.

Format: `recipe reset`

![recipeReset.png](images/recipeReset.png)

### Delete a Recipe: `recipe delete`

Deletes a recipe from the program.

Format: `recipe delete <recipeName>`

Example:

- `recipe delete testRecipe` Deletes the recipe with the name `testRecipe`.
- 
![recipeDelete.png](images/recipeDelete.png)

## Saving Recipe to File

Saves the recipe into a file. However, this is done automatically for you, so that it is less punishing for you (especially if you often forget to save your documents before exiting).

How it works:

Every time you add a recipe into Decodex, it will save this new recipe into a text file, with the recipe name as the file name, in the `recipe/` folder. Furthermore, any adding or removing of modules from this recipe would also update the corresponding recipe save file.

Example:

If you create a new recipe called "onlyForMe" in Decodex, this will create "onlyForMe.txt" which you can find in the `recipe/` folder. And whenever you run the `recipe push` or `recipe pop` command, it will update "onlyForMe.txt" accordingly.

## Reading/writing data from/to file `[coming soon in the future]`

Coming soon to a cinema near you in the future*…*

## Command Summary

| Action                   | Command Format        | Example Usage                                                |
| ------------------------ | --------------------- | ------------------------------------------------------------ |
| Enter input data         | `input <data>`        | `input SGVsbG9Xb3JsZA==`<br><code>input  hello world </code> |
| List available modules   | `list`                | `list`                                                       |
| Select processing module | `select <moduleName>` | `select base64decode`<br>`select hexencode`                  |
| Reset to original data   | `reset`               | `reset`                                                      |
| Exit program             | `exit`                | `exit`                                                       |