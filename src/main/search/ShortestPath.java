package search;

import graph.Graph;
import list.ArrayList;
import list.List;
import map.HashMap;
import map.Map;
import queue.LinkedQueue;
import queue.Queue;
import set.HashSet;
import set.Set;

/** Recovering a path with the fewest edges, from a breadth-first search. */
public final class ShortestPath {

  private ShortestPath() {
    // This class should not be instantiated!
  }

  // Returns a map from each vertex reachable from start, other than start, to
  // the vertex it was first reached from. Assumes graph is not null and start
  // is a vertex of graph.
  public static <V> Map<V, V> predecessors(Graph<V> graph, V start) {
    Set<V> visited = new HashSet<>();
    Map<V, V> predecessor = new HashMap<>();
    Queue<V> toProcess = new LinkedQueue<>();

    visited.add(start);
    toProcess.enqueue(start);

    while (!toProcess.isEmpty()) {
      V v = toProcess.front();
      toProcess.dequeue();
      for (V neighbor : graph.neighbors(v)) {
        if (!visited.contains(neighbor)) {
          visited.add(neighbor);
          predecessor.put(neighbor, v);  // reached neighbor from v
          toProcess.enqueue(neighbor);
        }
      }
    }
    return predecessor;
  }

  // Returns the path from start to target, read from a predecessor map built
  // by a search from start. Returns an empty list if target was not reached.
  // Assumes predecessor, start, and target are not null.
  public static <V> List<V> pathTo(Map<V, V> predecessor, V start, V target) {
    List<V> path = new ArrayList<>();
    if (!target.equals(start) && !predecessor.containsKey(target)) {
      return path;  // target unreachable: no path
    }

    V step = target;
    path.add(step);
    while (!step.equals(start)) {
      step = predecessor.get(step);
      path.add(step);
    }
    reverse(path);
    return path;
  }

  // Reverses the order of the elements of list, in place.
  private static <V> void reverse(List<V> list) {
    for (int i = 0, j = list.size() - 1; i < j; i++, j--) {
      V temp = list.get(i);
      list.set(i, list.get(j));
      list.set(j, temp);
    }
  }
}
