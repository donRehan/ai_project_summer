# Update

``` bash
[I] ~/d/ai_project_summer (main)> ./run.sh execute
Note: Some input files use unchecked or unsafe operations.
Note: Recompile with -Xlint:unchecked for details.
Test Failed
The output is: ->change->down->AgentChange->->right->right
```
Test succeeeds when the output is as follows 
``` java
String test = "->change->down->AgentChange->right->right";
if (bf_Search(problem).equals(test)) {
    System.out.println("Test Passed");
} else {
    System.out.println("Test Failed");
    System.out.println("The output is: " + bf_Search(problem));
}
```

Hence my only issue now is after AgentChange I have two -> instead of one.
After this the goal is to create a test with horizontal movements.
