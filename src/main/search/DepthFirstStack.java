package search;

import graph.Graph;
import set.HashSet;
import set.Set;
import stack.ArrayStack;
import stack.Stack;

/** Depth-first search, written with an explicit stack. */
public final class DepthFirstStack {

  private DepthFirstStack() {
    // This class should not be instantiated!
  }

  // Returns the set of vertices reachable from start, including start.
  // Assumes graph is not null and start is a vertex of graph.
  public static <V> Set<V> depthFirst(Graph<V> graph, V start) {
    Set<V> visited = new HashSet<>();
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
    return visited;
  }
}
