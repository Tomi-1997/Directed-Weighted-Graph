# Directed Weighted Graph
This project aims to construct a graph, perform certain alogirthms on it, and to simulate it through a graphical user interface.

### How to run
Download the jar file and navigate to the file's location through the command line and type: <br />
java -jar Ex2.jar "JsonWithDataOfGraph.json" <br />

Or type the same line with no arguments for a random graph and then load from the menu or roll a new graph. <br />
java -jar Ex2.jar

## Graph implementation
Our method of representing a graph with a group of edges and vertices, G (V,E) is with two hashmaps:
One hash map for the edges for which each Edge has a string key containing the source vertex and destination vertex.
And a second hash map for the vertices for which each Vertex has a unique id, weight and position in a 2D plane.

Old version:
![Demo](https://github.com/bfwontcodewithme/Ex2_OOP/blob/main/Examples/Psuedo-3d%20graph%20simulation_2.gif)

Newer Version:
Example for a small graph:
![Demo](https://github.com/bfwontcodewithme/Ex2_OOP/blob/main/Examples/Example1.png)

Same graph but with shortestpath from node 8 to node 13 highlighted:
![Demo](https://github.com/bfwontcodewithme/Ex2_OOP/blob/main/Examples/Example2_path_8_13.png)

Example for a large graph with center (orange) , path between two nodes (green) , and a list of nodes that contains nodes 7,35,45 in blue.
![Demo](https://github.com/bfwontcodewithme/Ex2_OOP/blob/main/Examples/Example3_tsp_center_shortestPath.png)


## Algorithm implementations
There are several methods to perform on our graph, such as:
### Connected
Can each pair of vertices reach one another?
### Shortest path between two vertices
Minimal distance between two vertices.
### Traveling salesman problem
Given a list of vertices, returns an approximation of the shortest path that passes through them.
### Which node is the center
Which vertex is the closest to all other vertices.

## Analysis of DWG with large group of vertices
Initialization:  <br />
1,000 : ~80 ms <br />
10,000 : ~400 ms <br />
100,000 : ~ 2 seconds <br />
1,000,000 : Out of memory error <br />

Shortest distance between two vertices: <br />
1,000 : ~ 450 ms  <br />
10,000 :  ~ 100 seconds <br />

Checking if the graph is connected:  <br />
1000 : ~900 ms <br />
10,000 :  2 ~ 3 minutes <br />


