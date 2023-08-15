// The code below can be cleaned to be more dry for later practice
// Add when an Object is of size 3 in movement
import java.util.ArrayList;

public class Node {

//tuple to store the size of the board
public int[] size_ofBoard = new int[2];
//Current Object of interest
public block currentObject;
//Agent Object to always know its location
public block agent;
public String sequence = "";
//MiniGoal Points
public int[] miniGoal = new int[2];
//Vertical Objects as an arraylist
public ArrayList<block> verticalObjects = new ArrayList<block>();
//Horizontal Objects
public ArrayList<block> horizontalObjects = new ArrayList<block>();

//initialize the node
//	public Node(int[] size, block currentObject){
//		size_ofBoard = size;
//		this.currentObject = currentObject;
//	}

// Most likely only used for the inital node.
public  Node(int[] size_ofBoard, block newBlock, String sequence)
{
this.size_ofBoard = size_ofBoard;
this.currentObject = newBlock;
this.sequence = sequence;
}

//This method removes an object if exists from the arraylist of the node
public synchronized void remove_object(ArrayList<block> objects, block cObject)
{
	for(int i = 0; i < objects.size(); i++)
	{
		if(objects.get(i).equals(cObject))
		{
			objects.remove(i);
			break;
		}
	}
}

//Method to copy arraylists using deep cloning
public synchronized ArrayList copy_arraylist(ArrayList<block> objects_Array)
{
ArrayList<block> cloned_array = new ArrayList<block>();
for (block obj : objects_Array) {
block ob = new block("block", obj.Direction, obj.StartPoint.clone(), obj.EndPoint.clone(), obj.Size);
cloned_array.add(ob);
}
return cloned_array;
}

//Potential function
public synchronized void add_object(ArrayList<block> objects, block cObject)
{
	objects.add(cObject);
}
//End of Potential function

//Method to create a directional node
public synchronized void create_node(ArrayList<Node> expandedNodes, int[] size_ofBoard, String sequence, block currentObject, int[] miniGoal, block agent, String new_direction){

	if(currentObject.T.equals("block")){
	block currentObjectClone = new block(currentObject.T, currentObject.Direction, currentObject.StartPoint.clone(), currentObject.EndPoint.clone(), currentObject.Size);
	//Clean the arraylists with correct values
	clean_arraylists(verticalObjects,horizontalObjects,currentObject,currentObjectClone);
	Node new_node = next_node(size_ofBoard.clone(), sequence, currentObjectClone, new_direction);
	//Copy the currentArraylists and pass them into the node
	ArrayList<block> clonedVerticalObjects = copy_arraylist(verticalObjects);
	ArrayList<block> clonedHorizontalObjects = copy_arraylist(horizontalObjects);
	new_node.verticalObjects = clonedVerticalObjects;
	new_node.horizontalObjects = clonedHorizontalObjects;
	//copy the current minigoal and pass it into the new node and then add the new node to the list of expaneded Nodes
	new_node.miniGoal =  miniGoal.clone();
	new_node.agent = new block(agent.T, agent.Direction, agent.StartPoint.clone(), agent.EndPoint.clone(), agent.Size);
	expandedNodes.add(new_node);
	}

	if(currentObject.T.equals("agent")){
	//block agentClone = new block(currentObject.T, currentObject.Direction, currentObject.StartPoint.clone(), currentObject.EndPoint.clone(), currentObject.Size);
	block agentClone = (block) currentObject.clone();
	//Create a new node with current node object but as a deep clone
	block currentObjectClone = new block(currentObject.T, currentObject.Direction, currentObject.StartPoint.clone(), currentObject.EndPoint.clone(), currentObject.Size);
	//Change the points of the object based on the movement
	//currentObjectClone = movement("right", currentObjectClone);
	//Create the node and increment its sequence by the word right
	Node right_node = next_node(size_ofBoard.clone(), sequence, currentObjectClone, "right");
	//Make sure to deep clone all other instance variables of the node and add them into the newly created node.
	ArrayList<block> clonedVerticalObjects = copy_arraylist(verticalObjects);
	ArrayList<block> clonedHorizontalObjects = copy_arraylist(horizontalObjects);
	right_node.verticalObjects = clonedVerticalObjects;
	right_node.horizontalObjects = clonedHorizontalObjects;
	right_node.agent = agentClone;
	expandedNodes.add(right_node);
	}
}

// Method to create motion by changing current object values and returning it with the new modifications
public synchronized block movement(String direction, block cObject)
{
// if direction is up
if(direction.equals("up"))
{
//Up meaning increase y of the start and end points by 1
cObject.StartPoint[1] = cObject.StartPoint[1] - 1;
//check if size is 3 increase also the middle point
cObject.EndPoint[1] = cObject.EndPoint[1] - 1;
}

// if direction is down
if(direction.equals("down"))
{
//Down meaning decrease y of the start and end points by 1	
cObject.StartPoint[1] = cObject.StartPoint[1] + 1;
//check if size is 3 increase also the middle point
cObject.EndPoint[1] = cObject.EndPoint[1] + 1;
}

// if direction is left
if(direction.equals("left"))
{
cObject.StartPoint[0] = cObject.StartPoint[0] - 1;
//handle if size is three increase also the middle point
cObject.EndPoint[0] = cObject.EndPoint[0] - 1;

}

// if direction is right
if(direction.equals("right"))
{
cObject.StartPoint[0] = cObject.StartPoint[0] + 1;
//if size is 3 increase also the middle point
cObject.EndPoint[0] = cObject.EndPoint[0] + 1;

}

if(direction.equals("change"))
{
	return cObject;
}

return cObject;

}

//Method to handle cleaning the arraylists when an object moves
public synchronized void clean_arraylists(ArrayList<block> verticalobjects,ArrayList<block> horizontalobjects,block curObject, block curObjectclone)
{
	//to be removed when code cleaning if not needed
	if(curObject.Direction == 'v'){
		remove_object(verticalobjects, curObject);
	}
	if(curObject.Direction == 'h'){
		remove_object(horizontalobjects, curObject);
	}
}

//Simple movement method , Returns a node as an output
public synchronized Node next_node(int[] board_size ,String seqe, block cObject, String direction)
{
block currentObjectClone = new block(cObject.T, cObject.Direction, cObject.StartPoint.clone(), cObject.EndPoint.clone(), cObject.Size);
currentObjectClone = movement(direction, currentObjectClone);
seqe = seqe + "->" + direction;
Node right_node = new Node(board_size, currentObjectClone, seqe);
return right_node;
}

public Node(String problem)
{
//split the string by ; into an array
String[] problemArray = problem.split(";");
//take the first element of the array and split it by ,	
this.size_ofBoard[0] = Integer.parseInt(problemArray[0].split(",")[1]);
this.size_ofBoard[1] = Integer.parseInt(problemArray[0].split(",")[0]);
//agent object ; Create an agent object and store it in the currentObject
//to be resolved later
currentObject = new block("agent", 'h', new int[] { 1, 2 }, new int[] { 2, 2 }, 2);
//loop through the rest of the array and create block objects
for(int i = 2; i < problemArray.length; i++)
{
//split the string by , and store the values in an array
String[] blockArray = problemArray[i].split(",");
//Print current blockArray for debugging
//System.out.println("Current Block Array: " + problemArray[i]);
//check the type from element 0 char 0 in block array and store it into a char variable
//and then check element 0 char 1 as the size of the block
char type = blockArray[0].charAt(0);
char size = blockArray[0].charAt(1);
//print those two chars each on separate lines
//System.out.println("Type: " + type);
//System.out.println("Size: " + size);
if(size == '3')
{
//expect 6 points ; store from 1 to 6 into their repsective points array
int[] startPoint = new int[] {Integer.parseInt(blockArray[1]), Integer.parseInt(blockArray[2])};
int[] endPoint = new int[] {Integer.parseInt(blockArray[3]), Integer.parseInt(blockArray[4])};
int[] middlePoint = new int[] {Integer.parseInt(blockArray[5]), Integer.parseInt(blockArray[6])};
//create the block object and add it into the node
block newBlock = new block("block", type, startPoint, middlePoint, endPoint, 3);
//add the block into the arraylist
if(type == 'h')
horizontalObjects.add(newBlock);
else
verticalObjects.add(newBlock);

}
if(size == '2')
{
//expect 4 points ; store from 1 to 4 into their repsective points array
int[] startPoint = new int[] {Integer.parseInt(blockArray[1]), Integer.parseInt(blockArray[2])};
int[] endPoint = new int[] {Integer.parseInt(blockArray[3]), Integer.parseInt(blockArray[4])};
//Print end and start points each on different lines
//System.out.println("Start Point: " + startPoint[0] + "," + startPoint[1]);
//System.out.println("End Point: " + endPoint[0] + "," + endPoint[1]);
//create the block object and add it into the node
block newBlock = new block("block", type, startPoint, endPoint, 2);
//add the block into the arraylist
if(type == 'h')
horizontalObjects.add(newBlock);
else
verticalObjects.add(newBlock);
}
}

}

//Next Milestone :: Multiple objects
//Need a method to get the currentObject class Easily done !
//Need a method that checks that the block is vertical or hoizontal
//Need a method to change the block of interest and its values.

//method to check exit
public boolean check_exit()
{
// to be changed when the size is dynamic
if(currentObject.EndPoint[0] == size_ofBoard[0])
return true;
else
return false;
}

//MiniGoal
public boolean Mini_Goal()
{
//if miniGoal is is not Occupied then return true
if(Occupied(miniGoal) == null)
return true;
else
return false;
}


//Method to check if an Object occupies this space and if so a returns that object
public block Occupied(int[] point)
{
//loop through the vertical objects and check if current points corleate to start end or middle points if any block
//Loop by blocks through the arraylist
for(block currentBlock : verticalObjects)
{
//check the size of currentBlock
if(currentBlock.Size == 2)
{
//check if the point is the start point or the end point
if(point[0] == currentBlock.StartPoint[0] && point[1] == currentBlock.StartPoint[1])
return currentBlock;
else if(point[0] == currentBlock.EndPoint[0] && point[1] == currentBlock.EndPoint[1])
return currentBlock;
}
else if(currentBlock.Size == 3)
{
//check if the point is the start point or the end point
if(point[0] == currentBlock.StartPoint[0] && point[1] == currentBlock.StartPoint[1])
return currentBlock;
else if(point[0] == currentBlock.EndPoint[0] && point[1] == currentBlock.EndPoint[1])
return currentBlock;
else if(point[0] == currentBlock.MiddlePoint[0] && point[1] == currentBlock.MiddlePoint[1])
return currentBlock;
}
}

//loop through the horizontal objects
for(block currentBlock : horizontalObjects)
{
//check the size of currentBlock
if(currentBlock.Size == 2)
{
//check if the point is the start point or the end point
if(point[0] == currentBlock.StartPoint[0] && point[1] == currentBlock.StartPoint[1])
return currentBlock;
else if(point[0] == currentBlock.EndPoint[0] && point[1] == currentBlock.EndPoint[1])
return currentBlock;
}
else if(currentBlock.Size == 3)
{
//check if the point is the start point or the end point
if(point[0] == currentBlock.StartPoint[0] && point[1] == currentBlock.StartPoint[1])
return currentBlock;
else if(point[0] == currentBlock.EndPoint[0] && point[1] == currentBlock.EndPoint[1])
return currentBlock;
else if(point[0] == currentBlock.MiddlePoint[0] && point[1] == currentBlock.MiddlePoint[1])
return currentBlock;
}
}

//remove me after done or leave it this way to catch it and handle such cases
return null;
}

//expanding nodes taking into account deep cloning blocks
//keep repeated states in mind.

public synchronized ArrayList expand() {
//Create an arraylist to store the expanded nodes
ArrayList<Node> expandedNodes = new ArrayList<Node>();

//MiniGoal check
if(Mini_Goal() && !(currentObject.T.equals("agent")))
{
	block agentClone = new block(agent.T, agent.Direction, agent.StartPoint.clone(), agent.EndPoint.clone(), agent.Size);
	//create a new node
	String seq = sequence + "->" + "AgentChange";
	//This part too creates an issue and creates an empty node.
	//All the below is shallow cloned and hence creates an empty objects
	//System.out.println("Here");
	Node next_node = new Node(size_ofBoard, agentClone, seq);
	//add the new node to the expanded nodes
	ArrayList<block> clonedVerticalObjects = copy_arraylist(verticalObjects);
	ArrayList<block> clonedHorizontalObjects = copy_arraylist(horizontalObjects);
	next_node.verticalObjects = clonedVerticalObjects;
	next_node.horizontalObjects = clonedHorizontalObjects;
	//Passed the agent location
	next_node.agent = agentClone;
	expandedNodes.add(next_node);
	return expandedNodes;
}


//check if the block is horizontal or vertical or if it is an agent
if(currentObject.T.equals("agent"))
{
//movements available for the agent left right
//Change it to less than or equal to 1 
//check if the agent can move left
//if(currentObject.StartPoint[0] != 1)
//{
//// If the left is not occupied then move the agent left
////get the left spot
//int[] left_spot = new int[] {currentObject.StartPoint[0] - 1, currentObject.EndPoint[1] - 1};
////check if the left spot is occupied
////Check if it is not occupied then you can move by doing necessary adjustments
//if(Occupied(left_spot) == null)
//{
////create a new block
//block currentObjectClone = new block(currentObject.T, currentObject.Direction, currentObject.StartPoint.clone(), currentObject.EndPoint.clone(), currentObject.Size);
////move the agent left
////deep cloning starting point and end point
//currentObjectClone.StartPoint[0] = currentObjectClone.StartPoint[0] - 1;
//currentObjectClone.EndPoint[0] = currentObjectClone.EndPoint[0] - 1;
////create a new node
//String seq = sequence + "Left";
//Node left_node = new Node(size_ofBoard, currentObjectClone, seq);
////add the new node to the expanded nodes
//expandedNodes.add(left_node);
//}
////else expand by making the object that occupies it the main object
////expand and making the object that blocks it as the main object of the next nodes
//else
//{
//block left_block = Occupied(left_spot);
//// Perform a deep clone of the currentObject
//block currentObjectClone = new block(currentObject.T, currentObject.Direction, currentObject.StartPoint.clone(), currentObject.EndPoint.clone(), currentObject.Size);
//currentObject = left_block;
//// Create a new block and deep clone the starting point and end point
//block newBlock = new block(currentObjectClone.T, currentObjectClone.Direction, currentObjectClone.StartPoint.clone(), currentObjectClone.EndPoint.clone(), currentObjectClone.Size);
////create a new node
//String seq = sequence + "AgentChange";
//Node left_node = new Node(size_ofBoard, newBlock, seq);
////add the new node to the expanded nodes
//left_node.miniGoal = left_spot.clone();
//expandedNodes.add(left_node);
//}
//
//}
//check if the agent can move right
if(currentObject.EndPoint[0] <= size_ofBoard[0])
{
// If the left is not occupied then move the agent left
//get the left spot
int[] right_spot = new int[] {currentObject.EndPoint[0] + 1, currentObject.EndPoint[1]};
//check if the left spot is occupied
//Check if it is not occupied then you can move by doing necessary adjustments
if(Occupied(right_spot) == null){
	create_node(expandedNodes, size_ofBoard, sequence, currentObject,  miniGoal, agent, "right");
}
else{
// I think pass mini goal and agentclone and thats it ?
//store the object that blocks the agent
//Diff
//Mini goal here = right_spot deep clone
//CurrentObjectClone = rightblock deep clone
//pass into the string "change"
//Bug ? ; now you need to add the current Object back into the arraylist ! Else it gets lost !?
block right_block = Occupied(right_spot);
block agentClone = (block) currentObject.clone();
//Create a new node with current node object but as a deep clone
block currentObjectClone = new block(right_block.T, right_block.Direction, right_block.StartPoint.clone(), right_block.EndPoint.clone(), right_block.Size);
//Create the node and increment its sequence by the word right
Node right_node = next_node(size_ofBoard.clone(), sequence, currentObjectClone, "change");
//Make sure to deep clone all other instance variables of the node and add them into the newly created node.
ArrayList<block> clonedVerticalObjects = copy_arraylist(verticalObjects);
ArrayList<block> clonedHorizontalObjects = copy_arraylist(horizontalObjects);
right_node.verticalObjects = clonedVerticalObjects;
right_node.horizontalObjects = clonedHorizontalObjects;
right_node.agent = agentClone;
right_node.miniGoal = new int[] {right_spot[0], right_spot[1]};
expandedNodes.add(right_node);
}
}
}
//if the block is horizontal

//check if the current object is an object and then check if it is horizontal and vertical objects	
if(currentObject.T.equals("block"))
{
//check if its horizontal or vertical and do the respective movements for it
//if(currentObject.Direction == 'h')

//if the current Object is a vertical one
if(currentObject.Direction == 'v')
{
	//Move up 
	//Call Sync Method to move up
	//Check if there is no border first
	if(currentObject.StartPoint[1] > 1)
	{
	//check if not the point above is not occupied
	//check if top spot is not out of borders
	int[] top_spot = new int[] {currentObject.StartPoint[0], currentObject.StartPoint[1] - 1};
	if(top_spot[1] <= 0)
	top_spot = null;
	//check if the top spot is occupied or not
	if(Occupied(top_spot) == null)
	{
	create_node(expandedNodes, size_ofBoard, sequence, currentObject,  miniGoal, agent, "up");

	}
	else
	{
	//store the object that blocks the agent
	block top_block = Occupied(top_spot);
	// Perform a deep clone of the agent
	block agentClone = new block(agent.T, agent.Direction, agent.StartPoint.clone(), agent.EndPoint.clone(), agent.Size);
	//change the current object to the object that blocks it by doing a deep clone on it
	currentObject = new block(top_block.T, top_block.Direction, top_block.StartPoint.clone(), top_block.EndPoint.clone(), top_block.Size);
	//Add that we changed the agent to the sequence
	String seq = sequence + "AgentChange";
	//Create a new node with the new object that blocks the agent being the main object
	Node up_node = new Node(size_ofBoard, currentObject, seq);
	//give the goal of the newly created node to be to leave the spot that it blocked the agent from reaching 
	up_node.miniGoal = top_spot.clone();
	//Get the vertical and horizontal objects cloned and add them to the newly created node
	ArrayList<block> clonedVerticalObjects = copy_arraylist(verticalObjects);
	ArrayList<block> clonedHorizontalObjects = copy_arraylist(horizontalObjects);
	up_node.verticalObjects = clonedVerticalObjects;
	up_node.horizontalObjects.add((block)currentObject);
	up_node.horizontalObjects = clonedHorizontalObjects;
	//Passed the agent location
	up_node.agent = agentClone;
	expandedNodes.add(up_node);
	}
	}

	//Move Down
	//Call Sync Method to move down
	if(currentObject.EndPoint[1] != size_ofBoard[1])
	{
	//check if not the point above is not occupied
	int[] down_spot = new int[] {currentObject.EndPoint[0], currentObject.EndPoint[1] + 1};
	if(down_spot[1] > size_ofBoard[1])
	down_spot = null;
	//check if the top spot is occupied or not
	if(Occupied(down_spot) == null)
	{
	//Some function
	//use create_node method and pass the correct values
	create_node(expandedNodes, size_ofBoard, sequence, currentObject,  miniGoal, agent, "down");
	}
	else
	{
	//Potential BUG
	//This is where it is jumping to , to go down instead of the block above
	//store the object that blocks the agent
	block down_block = Occupied(down_spot);
	// Perform a deep clone of the agent
	block agentClone = new block(agent.T, agent.Direction, agent.StartPoint.clone(), agent.EndPoint.clone(), agent.Size);
	//change the current object to the object that blocks it by doing a deep clone on it
	currentObject = new block(down_block.T, down_block.Direction, down_block.StartPoint.clone(), down_block.EndPoint.clone(), down_block.Size);
	//Add that we changed the agent to the sequence
	String seq = sequence + "Here";
	//Create a new node with the new object that blocks the agent being the main object
	Node up_node = new Node(size_ofBoard, currentObject, seq);
	//give the goal of the newly created node to be to leave the spot that it blocked the agent from reaching 
	up_node.miniGoal = down_spot.clone();
	//Get the vertical and horizontal objects cloned and add them to the newly created node
	ArrayList<block> clonedVerticalObjects = copy_arraylist(verticalObjects);
	ArrayList<block> clonedHorizontalObjects = copy_arraylist(horizontalObjects);
	up_node.verticalObjects = clonedVerticalObjects;
	up_node.horizontalObjects.add((block)currentObject);
	up_node.horizontalObjects = clonedHorizontalObjects;
	//Passed the agent location
	up_node.agent = agentClone;
	expandedNodes.add(up_node);
	}
	}

	//End of Vertical block method
}
}

//if null return null
if (expandedNodes.isEmpty())
return null;

return expandedNodes;
}

}
