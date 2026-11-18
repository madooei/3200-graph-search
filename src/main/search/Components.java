package search;

import graph.Graph;
import set.HashSet;
import set.Set;
import stack.ArrayStack;
import stack.Stack;

/** Counting the connected components of an undirected graph. */
public final class Components {

  private Components() {
    // This class should not be instantiated!
  }

  // Returns the number of connected components of graph. Assumes graph is not
  // null and is undirected, with each edge stored in both directions.
  public static <V> int countComponents(Graph<V> graph) {
    Set<V> visited = new HashSet<>();
    int count = 0;

    for (V v : graph.vertices()) {
      if (!visited.contains(v)) {
        count++;
        markReachable(graph, v, visited);
      }
    }
    return count;
  }

  // Adds to visited every vertex reachable from start that is not already in it.
  private static <V> void markReachable(Graph<V> graph, V start, Set<V> visited) {
    Stack<V> toVisit = new ArrayStack<>();
    visited.add(start);
    toVisit.push(start);
    while (!toVisit.isEmpty()) {
      V v = toVisit.top();
      toVisit.pop();
      for (V neighbor : graph.neighbors(v)) {
        if (!visited.contains(neighbor)) {
          visited.add(neighbor);
          toVisit.push(neighbor);
        }
      }
    }
  }
}
