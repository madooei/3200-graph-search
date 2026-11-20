package search;

import graph.AdjacencyListGraph;
import graph.Graph;
import list.ArrayList;
import list.List;
import map.Map;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Tests for recovering a path with the fewest edges. */
public class ShortestPathTest {

  @Test
  public void predecessorsHasNoEntryForTheStart() {
    Map<Integer, Integer> predecessor = ShortestPath.predecessors(diamond(), 0);
    assertFalse(predecessor.containsKey(0));
  }

  @Test
  public void predecessorsRecordsTheVertexANeighborWasReachedFrom() {
    Map<Integer, Integer> predecessor = ShortestPath.predecessors(diamond(), 0);
    assertEquals(0, predecessor.get(1));
  }

  @Test
  public void predecessorsHasAnEntryForEveryOtherReachableVertex() {
    Map<Integer, Integer> predecessor = ShortestPath.predecessors(diamond(), 0);
    assertEquals(3, predecessor.size());
  }

  @Test
  public void predecessorsHasNoEntryForAnUnreachableVertex() {
    Map<Integer, Integer> predecessor = ShortestPath.predecessors(lineWithLoneVertex(), 0);
    assertFalse(predecessor.containsKey(4));
  }

  @Test
  public void pathToTheStartIsJustTheStart() {
    Map<Integer, Integer> predecessor = ShortestPath.predecessors(diamond(), 0);
    List<Integer> path = ShortestPath.pathTo(predecessor, 0, 0);
    assertEquals(1, path.size());
  }

  @Test
  public void pathToANeighborHasTwoVertices() {
    Map<Integer, Integer> predecessor = ShortestPath.predecessors(diamond(), 0);
    List<Integer> path = ShortestPath.pathTo(predecessor, 0, 1);
    assertEquals(2, path.size());
  }

  @Test
  public void pathStartsAtTheStart() {
    Map<Integer, Integer> predecessor = ShortestPath.predecessors(lineWithLoneVertex(), 0);
    List<Integer> path = ShortestPath.pathTo(predecessor, 0, 3);
    assertEquals(0, path.get(0));
  }

  @Test
  public void pathEndsAtTheTarget() {
    Map<Integer, Integer> predecessor = ShortestPath.predecessors(lineWithLoneVertex(), 0);
    List<Integer> path = ShortestPath.pathTo(predecessor, 0, 3);
    assertEquals(3, path.get(3));
  }

  @Test
  public void pathListsTheVerticesBetweenInOrder() {
    Map<Integer, Integer> predecessor = ShortestPath.predecessors(lineWithLoneVertex(), 0);
    List<Integer> path = ShortestPath.pathTo(predecessor, 0, 3);
    assertEquals(1, path.get(1));
  }

  @Test
  public void pathHasTheFewestEdges() {
    // In the diamond, 3 is two edges from 0; the shortcut 0 -> 3 makes it one.
    Graph<Integer> graph = diamond();
    graph.addEdge(0, 3);
    Map<Integer, Integer> predecessor = ShortestPath.predecessors(graph, 0);
    List<Integer> path = ShortestPath.pathTo(predecessor, 0, 3);
    assertEquals(2, path.size());
  }

  @Test
  public void pathToFiveTakesTheShorterBranch() {
    Map<Integer, Integer> predecessor = ShortestPath.predecessors(crossedBranches(), 0);
    List<Integer> path = ShortestPath.pathTo(predecessor, 0, 5);
    assertEquals(3, path.size());
  }

  @Test
  public void pathToSixTakesTheShorterBranch() {
    Map<Integer, Integer> predecessor = ShortestPath.predecessors(crossedBranches(), 0);
    List<Integer> path = ShortestPath.pathTo(predecessor, 0, 6);
    assertEquals(3, path.size());
  }

  @Test
  public void firstStepOfAPathIsAnEdge() {
    Graph<Integer> graph = diamond();
    Map<Integer, Integer> predecessor = ShortestPath.predecessors(graph, 0);
    List<Integer> path = ShortestPath.pathTo(predecessor, 0, 3);
    assertTrue(graph.containsEdge(path.get(0), path.get(1)));
  }

  @Test
  public void lastStepOfAPathIsAnEdge() {
    Graph<Integer> graph = diamond();
    Map<Integer, Integer> predecessor = ShortestPath.predecessors(graph, 0);
    List<Integer> path = ShortestPath.pathTo(predecessor, 0, 3);
    assertTrue(graph.containsEdge(path.get(1), path.get(2)));
  }

  @Test
  public void pathToAnUnreachableVertexIsEmpty() {
    Map<Integer, Integer> predecessor = ShortestPath.predecessors(lineWithLoneVertex(), 0);
    List<Integer> path = ShortestPath.pathTo(predecessor, 0, 4);
    assertEquals(0, path.size());
  }

  @Test
  public void findPathToTheStartIsJustTheStart() {
    List<Integer> path = ShortestPath.findPath(diamond(), 0, 0);
    assertEquals(1, path.size());
  }

  @Test
  public void findPathStartsAtTheStart() {
    List<Integer> path = ShortestPath.findPath(lineWithLoneVertex(), 0, 3);
    assertEquals(0, path.get(0));
  }

  @Test
  public void findPathEndsAtTheTarget() {
    List<Integer> path = ShortestPath.findPath(lineWithLoneVertex(), 0, 3);
    assertEquals(3, path.get(3));
  }

  @Test
  public void findPathListsTheVerticesBetweenInOrder() {
    List<Integer> path = ShortestPath.findPath(lineWithLoneVertex(), 0, 3);
    assertEquals(1, path.get(1));
  }

  @Test
  public void findPathToFiveTakesTheShorterBranch() {
    List<Integer> path = ShortestPath.findPath(crossedBranches(), 0, 5);
    assertEquals(3, path.size());
  }

  @Test
  public void findPathToSixTakesTheShorterBranch() {
    List<Integer> path = ShortestPath.findPath(crossedBranches(), 0, 6);
    assertEquals(3, path.size());
  }

  @Test
  public void findPathToAnUnreachableVertexIsEmpty() {
    List<Integer> path = ShortestPath.findPath(lineWithLoneVertex(), 0, 4);
    assertEquals(0, path.size());
  }

  @Test
  public void findPathStopsWhenTheTargetComesOut() {
    // In the line 0 -> 1 -> 2 -> 3, a search for 1 processes 0 and then 1.
    // It must stop there, so it asks only 0 for its neighbors.
    final List<Integer> asked = new ArrayList<>();
    Graph<Integer> graph = new AdjacencyListGraph<Integer>() {
      @Override
      public Iterable<Integer> neighbors(Integer vertex) {
        asked.add(vertex);
        return super.neighbors(vertex);
      }
    };
    for (int vertex = 0; vertex < 4; vertex++) {
      graph.addVertex(vertex);
    }
    graph.addEdge(0, 1);
    graph.addEdge(1, 2);
    graph.addEdge(2, 3);
    ShortestPath.findPath(graph, 0, 1);
    assertEquals(1, asked.size());
  }

  // The directed diamond 0 -> 1, 0 -> 2, 1 -> 3, 2 -> 3.
  private Graph<Integer> diamond() {
    Graph<Integer> graph = vertices(4);
    graph.addEdge(0, 1);
    graph.addEdge(0, 2);
    graph.addEdge(1, 3);
    graph.addEdge(2, 3);
    return graph;
  }

  // The directed line 0 -> 1 -> 2 -> 3, and the vertex 4 with no edges.
  private Graph<Integer> lineWithLoneVertex() {
    Graph<Integer> graph = vertices(5);
    graph.addEdge(0, 1);
    graph.addEdge(1, 2);
    graph.addEdge(2, 3);
    return graph;
  }

  // Two branches from 0, through 1 and through 2. Vertex 5 is one edge past 2
  // and two edges past 1; vertex 6 is one edge past 1 and two edges past 2.
  // A search that goes deep down one branch first reaches one of them the long way.
  private Graph<Integer> crossedBranches() {
    Graph<Integer> graph = vertices(7);
    graph.addEdge(0, 1);
    graph.addEdge(0, 2);
    graph.addEdge(2, 5);
    graph.addEdge(1, 3);
    graph.addEdge(3, 5);
    graph.addEdge(1, 6);
    graph.addEdge(2, 4);
    graph.addEdge(4, 6);
    return graph;
  }

  // A graph with the vertices 0 through count - 1 and no edges.
  private Graph<Integer> vertices(int count) {
    Graph<Integer> graph = new AdjacencyListGraph<>();
    for (int vertex = 0; vertex < count; vertex++) {
      graph.addVertex(vertex);
    }
    return graph;
  }
}
