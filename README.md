# Directed Weighted Graph
This project aims to construct a graph, perform certain alogirthms on it, and to simulate it through a graphical user interface.

### How to run
Download the jar file and navigate to the file's location in commands line and type: <br />
java -jar Ex2.jar "your json file of a graph" <br />

Or type the same line with no arguments for a random graph. <br />
java -jar Ex2.jar

## Graph implementation
Our method of representing a graph with a group of edges and vertices, G (V,E) is with two hashmaps:
One hash map for the edges for which each Edge has a string key containing the source vertex and destination vertex.
And a second hash map for the vertices for which each Vertex has a unique id, weight and position in a 2D plane.

![Demo](https://github.com/bfwontcodewithme/Ex2_OOP/blob/main/Psuedo-3d%20graph%20simulation_2.gif)

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
