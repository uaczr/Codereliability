# Codereliability
Clang-Tidy as external Codeanalysis Tool in Eclipse-CDT Neon.3

## Requirements

- clang-tidy
- bear

## Usage
Copy jar file from Codereliability/deploy into your eclipse/Dropins folder.


Clang-Tidy should now be visible in Codan Properties menu, under
**Properties** --> **C/C++ General** --> **Code Analysis** --> **ClangTidy**.
Detected problems will now be shown per file in the cdt problems window.

Projects which use the ClangTidy Checker should use gnu make as external builder.
The build command should be the following **bear -o ../compile_commands.json make**, this will extract the compile commands database needed by clang-tidy. You can switch to the gnu make builder under **Properties --> C/C++ Build --> Tool Chain Editor**, the make command can then be ajusted to **bear -o ../compile_commands.json make** under **Properties --> C/C++ Build**.


**This is an early version, bugs are a possiblity.**
