package util;

import java.util.*;

public class CircuitSearch {
    private static int vertexNumber;
    private static int[] group;
    private static int[] low;
    private static boolean[] onStack;
    private static int index;
    private static Deque<Integer> stack;
    private static boolean[][] graph;
    private static List<Set<Integer>> result;

    public static List<Set<Integer>> findCircuits(boolean[][] graph) {
        CircuitSearch.graph = graph;
        vertexNumber = graph.length;
        group = new int[vertexNumber];
        low = new int[vertexNumber];
        onStack = new boolean[vertexNumber];
        result = new ArrayList<>();

        index = 1;
        stack = new LinkedList<>();

        for (int i = 0; i < vertexNumber; i++) {
            if (group[i] == 0) {
                dfs(i);
            }
        }

        return result;
    }

    private static void dfs(int vertex) {
        group[vertex] = index;
        low[vertex] = index;
        index++;
        stack.push(vertex);
        onStack[vertex] = true;

        for (int i = 0; i < vertexNumber; i++) {
            if (graph[vertex][i]) {
                if (group[i] == 0) {
                    dfs(i);
                    low[vertex] = Math.min(low[vertex], low[i]);
                } else if (onStack[i]) {
                    low[vertex] = Math.min(low[vertex], group[i]);
                }
            }
        }

        if (low[vertex] == group[vertex]) {
            Set<Integer> newGroup = new HashSet<>();
            int groupVertex;
            do {
                groupVertex = stack.pop();
                onStack[groupVertex] = false;
                newGroup.add(groupVertex);
            } while (groupVertex != vertex);

            result.add(newGroup);
        }
    }
}
