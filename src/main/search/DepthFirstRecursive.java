package search;

import graph.Graph;
import set.HashSet;
import set.Set;

/** Depth-first search, written recursively. */
public final class DepthFirstRecursive {

  private DepthFirstRecursive() {
    // This class should not be instantiated!
  }

  // Returns the set of vertices reachable from start, including start.
  // Assumes graph is not null and start is a vertex of graph.
  public static <V> Set<V> depthFirst(Graph<V> graph, V start) {
    Set<V> visited = new HashSet<>();
    visit(graph, start, visited);
    return visited;
  }

  // Marks v as visited, then visits each of its neighbors not yet visited.
  private static <V> void visit(Graph<V> graph, V v, Set<V> visited) {
    visited.add(v);
    for (V neighbor : graph.neighbors(v)) {
      if (!visited.contains(neighbor)) {
        visit(graph, neighbor, visited);
      }
    }
  }
}
