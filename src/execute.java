import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import java.util.PriorityQueue;
import java.util.Comparator;

public class execute {

//HashMap<LinkedList<Integer>, String> myGrid = new HashMap<>();

//Search Strategies
public static String bf_Search(String problem)
{
Queue<Object> Nodes = new LinkedList<Object>();
//Create a hashset to store the visited nodes
HashSet<Node> visited = new HashSet<>();

//Adding the initial state to the queue
//Create an inital Node from problem
Node initial = new Node(problem);
//add
Nodes.add(initial);
visited.add(initial);

//while the queue is not empty loop
while(!Nodes.isEmpty())
{
Node node = (Node) Nodes.remove();
//Check for the goal and do actions on the current node.
//to have an actual goal test method when I am almost finished.
if(node.check_exit()) 
{
//remove node.sequence last character
//System.out.println(node.currentObject.EndPoint[0]);
return node.sequence;
}

ArrayList<Node> children = node.expand();

if(children != null)
{
for(int i = 0; i < children.size(); i++)
{
//if the child is not in the visited set, add it to the queue
if(!visited.contains(children.get(i)))
{
Nodes.add(children.get(i));
visited.add(children.get(i));
}
}
}
}
//if it reaches here then there is no solution
return "The output actions do not lead to a goal state";
}

public static String df_Search(String problem)
{
    LinkedList<Object> Nodes = new LinkedList<Object>();
    HashSet<String> visited = new HashSet<String>();
    //Adding the initial state to the queue
    //Create an inital Node from problem
    Node initial = new Node(problem);
    //add to the stack initial 
    Nodes.addLast(initial);
    visited.add(initial.toString());
    //while the queue is not empty loop
    while(!Nodes.isEmpty())
    {
    Node node = (Node) Nodes.removeLast();

    //Check for the goal and do actions on the current node.
	if(node.check_exit()) 
	{
	//remove node.sequence last character
	//System.out.println(node.currentObject.EndPoint[0]);
	return node.sequence;
	}
    ArrayList<Node> children = node.expand();

    if(children != null)
    {
        for(int i = 0; i < children.size(); i++)
        {
            //if the child is not in the visited set, add it to the queue
            if(!visited.contains(children.get(i).toString()))
            {
                Nodes.addLast(children.get(i));
                visited.add(children.get(i).toString());
            }
        }
    }
    }
    //if it reaches here then there is no solution
    return "The output actions do not lead to a goal state.";
}

public static String iddfs_Search(String problem, int maxDepth) {
	for (int depth = 0; depth <= maxDepth; depth++) {
		String result = dls_Search(problem, depth);
		if (!result.equals("NO_SOLUTION")) {
			return result;
		}
	}
	return "The output actions do not lead to a goal state";
}

public static String dls_Search(String problem, int depthLimit) {
	Stack<Node> Nodes = new Stack<>();
	HashSet<Node> visited = new HashSet<>();

	Node initial = new Node(problem);
	Nodes.push(initial);
	visited.add(initial);

	while (!Nodes.isEmpty()) {
		Node node = Nodes.pop();

		if (node.check_exit()) {
			return node.sequence;
		}

		if (node.sequence.split("->").length - 1 < depthLimit) {
			ArrayList<Node> children = node.expand();

			if (children != null) {
				for (int i = 0; i < children.size(); i++) {
					if (!visited.contains(children.get(i))) {
						Nodes.push(children.get(i));
						visited.add(children.get(i));
					}
				}
			}
		}
	}
	return "NO_SOLUTION";
}

public static String ucs_Search(String problem) {
    PriorityQueue<Node> Nodes = new PriorityQueue<>(Comparator.comparingInt(node -> getPathCost(node.sequence)));
    HashSet<Node> visited = new HashSet<>();

    Node initial = new Node(problem);
    Nodes.add(initial);
    visited.add(initial);

    while (!Nodes.isEmpty()) {
        Node node = Nodes.poll();

        if (node.check_exit()) {
            return node.sequence;
        }

        ArrayList<Node> children = node.expand();

        if (children != null) {
            for (Node child : children) {
                if (!visited.contains(child)) {
                    Nodes.add(child);
                    visited.add(child);
                }
            }
        }
    }
    return "The output actions do not lead to a goal state";
}

private static int getPathCost(String sequence) {
    // Calculate the path cost based on the length of the sequence
    return sequence.split("->").length;
}

public static String GenGrid() {

// generate grid with random size
//int size = ThreadLocalRandom.current().nextInt(4, Integer.MAX_VALUE);
int size = 4;
//int noOfFurniture = ThreadLocalRandom.current().nextInt(1, size / 2);
int noOfFurniture = 2;
//int direction = ThreadLocalRandom.current().nextInt(0, 1); // 1 for horizontal and 0 for vertical

//	for (int i = 0; i < noOfFurniture; i++) {
//		int noOfCell = ThreadLocalRandom.current().nextInt(2, 3);

//		int xLocation = ThreadLocalRandom.current().nextInt(1, size);
//		int yLocation = ThreadLocalRandom.current().nextInt(1, size);

//		LinkedList<Integer> location = new LinkedList<>();
//		location.add(xLocation);
//		location.add(yLocation);

//		myGrid.put(location, "Furn1");
//	}

//handle object creation later

block agent = new block("agent",'h', new int[] { 1, 2 }, new int[] { 2, 2 }, 2);

String myGrid = size + "," + size + ";" + agent.agent_GetPoints() + ";" + "v2" + "," + "3,1,3,2;";// + "h2" + "," + "2,4,3,4;";
return myGrid;
}

//	public void Search(int grid, String strategy, Boolean visualize) {
//
//	}

public static void main(String[] args) {
String problem = GenGrid();
//System.out.println(problem);
//create node with problem string
//	Node initial = new Node(problem);
//	//return the result of calling Occupied on points 3,2 3,3
//	if(initial.Occupied(new int[] {3,4})== null)
//	{
//		System.out.println("not Occupied");
//	}
//	else
//	{
//		System.out.println("occupied");
//		//Print all the points of the object
//		System.out.println(initial.Occupied(new int[] {3,2}).agent_GetPoints());
//	}

//String solution = ucs_Search(problem);
//System.out.println("UCS" + solution);
//int maxDepth = 8; // Set your desired maximum depth
//String solution = iddfs_Search(problem, maxDepth);
//System.out.println("Solution: " + solution);
System.out.println(bf_Search(problem));
}

}
