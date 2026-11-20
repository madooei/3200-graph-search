package search;

import graph.AdjacencyListGraph;
import graph.Graph;
import set.Set;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The scenarios for a search that returns the set of reachable vertices. A
 * concrete subclass supplies search() to pick the search under test.
 */
public abstract class GraphSearchTest {

  // Calls the search under test.
  protected abstract Set<Integer> search(Graph<Integer> graph, int start);

  @Test
  public void searchOfALoneVertexReachesOnlyItself() {
    Graph<Integer> graph = new AdjacencyListGraph<>();
    graph.addVertex(7);
    Set<Integer> visited = search(graph, 7);
    assertEquals(1, visited.size());
  }

  @Test
  public void searchReachesTheStart() {
    Set<Integer> visited = search(exampleGraph(), 0);
    assertTrue(visited.contains(0));
  }

  @Test
  public void searchReachesANeighborOfTheStart() {
    Set<Integer> visited = search(exampleGraph(), 0);
    assertTrue(visited.contains(1));
  }

  @Test
  public void searchReachesAVertexSeveralEdgesAway() {
    Set<Integer> visited = search(exampleGraph(), 0);
    assertTrue(visited.contains(6));
  }

  @Test
  public void searchReachesEveryVertexOfAConnectedGraph() {
    Set<Integer> visited = search(exampleGraph(), 0);
    assertEquals(7, visited.size());
  }

  @Test
  public void searchOnAGraphWithACycleFinishes() {
    Graph<Integer> graph = triangle();
    Set<Integer> visited = search(graph, 0);
    assertEquals(3, visited.size());
  }

  @Test
  public void searchStopsAtTheEdgeOfAComponent() {
    Set<Integer> visited = search(twoPieces(), 0);
    assertEquals(2, visited.size());
  }

  @Test
  public void searchDoesNotReachAnotherComponent() {
    Set<Integer> visited = search(twoPieces(), 0);
    assertFalse(visited.contains(2));
  }

  @Test
  public void directedSearchFollowsEdgesForward() {
    Set<Integer> visited = search(oneWayLine(), 0);
    assertEquals(3, visited.size());
  }

  @Test
  public void directedSearchDoesNotFollowEdgesBackward() {
    Set<Integer> visited = search(oneWayLine(), 2);
    assertEquals(1, visited.size());
  }

  // The chapter's example: seven vertices, 0 through 6, all reachable from 0.
  private Graph<Integer> exampleGraph() {
    Graph<Integer> graph = vertices(7);
    addUndirected(graph, 0, 1);
    addUndirected(graph, 0, 2);
    addUndirected(graph, 0, 3);
    addUndirected(graph, 1, 4);
    addUndirected(graph, 3, 5);
    addUndirected(graph, 5, 6);
    addUndirected(graph, 4, 6);
    return graph;
  }

  // The triangle 0, 1, 2.
  private Graph<Integer> triangle() {
    Graph<Integer> graph = vertices(3);
    addUndirected(graph, 0, 1);
    addUndirected(graph, 1, 2);
    addUndirected(graph, 2, 0);
    return graph;
  }

  // Two components: the edge 0-1 and the edge 2-3.
  private Graph<Integer> twoPieces() {
    Graph<Integer> graph = vertices(4);
    addUndirected(graph, 0, 1);
    addUndirected(graph, 2, 3);
    return graph;
  }

  // The directed line 0 -> 1 -> 2.
  private Graph<Integer> oneWayLine() {
    Graph<Integer> graph = vertices(3);
    graph.addEdge(0, 1);
    graph.addEdge(1, 2);
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

  private void addUndirected(Graph<Integer> graph, int first, int second) {
    graph.addEdge(first, second);
    graph.addEdge(second, first);
  }
}
