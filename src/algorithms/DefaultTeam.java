package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Stream;

public class DefaultTeam {
	public static int THRESHOLD = 100;
	public List<List<Integer>> computeLinks(List<Point> pts) {
		
		List<List<Integer>> links = new ArrayList<List<Integer>> ();
		int thres = THRESHOLD*THRESHOLD;
		
		for (int i = 0; i < pts.size(); i++) {
			List<Integer> listI = new ArrayList<Integer>();
			
			for (int j = 0; j < pts.size(); j++) {
				if (i != j && pts.get(i).distanceSq(pts.get(j)) < thres) {
					listI.add(j);
				}
			}
			
			links.add(listI);
		}
		
		return links;
	}
	
//	private static int nbCycles = 0;
//	public void countCycles(ArrayList<Point> pts, List<List<Integer>> succ, int parent, boolean[] visited) {
//		
//		for (int i = 0; i < pts.size(); i++) {
//			if (visited[i]) return;
//			visited[i] = true;
//			
//			for (int s : succ.get(i)) { 
//				if (s == parent) continue;
//				if (visited[s]) {
//					System.out.println(s);
//					System.out.println(i);
//					nbCycles++;
//				}
//				else {
//					countCycles(pts, succ, i, visited);
//				}
//			}
//		}
//	}
//	
//	private int count(ArrayList<Point> pts, List<List<Integer>> succ) {
//		int nbCycles = 0;
//		Stack<Integer> toVisit = new Stack<>();
//		toVisit.push(0);
//		int parent = -1;
//		
//		while (!toVisit.isEmpty()) {
//			int pt = toVisit.pop();
//			
//			List<Integer> succs = succ.get(pt);
//			for (int i = 0; i < succs.size(); i++) {
//				if (parent == succs.get(i)) continue;
//			}
//		}
//		
//		return nbCycles;
//	}
	public ArrayList<Point> greedyFVS(ArrayList<Point> points) {
		ArrayList<Point> fvs = new ArrayList<Point>();
	    ArrayList<Point> rest = (ArrayList<Point>) points.clone();
	    
	    while (!Evaluation.isValide(rest, fvs)) {
	    		Point p_max = rest.get(0);
	    		for (Point p : rest) {
	    			if (Evaluation.neighbor(p, rest).size() > Evaluation.neighbor(p_max, rest).size()) {
	    				p_max = p;
	    			}
	    		}
	    		rest.remove(p_max);
	    		fvs.add(p_max);
	    }
	    
	    return fvs;
	}
	
	public int cycleness(ArrayList<Point> points) {
		int cycleness = 0;
		Set<Point> visited = new HashSet<>();
		
		for (Point source : points) {
			if (visited.contains(source)) continue;
			
			Queue<Point> queue = new LinkedList<>();
			queue.add(source);
		
			while(!queue.isEmpty()) {
				Point p = queue.remove();
				if (visited.contains(p)) {
					cycleness++;
				} else {
					visited.add(p);
					List<Point> neighbors = Evaluation.neighbor(p, points);
					for(Point q : neighbors) {
						if (!visited.contains(q)) {
							queue.add(q);
						}
					}
				}
			}
		}
		
		return cycleness;
	}
	
	public ArrayList<Point> greedyByCycleness(ArrayList<Point> points) {
		ArrayList<Point> fvs = new ArrayList<Point>();
	    ArrayList<Point> rest = (ArrayList<Point>) points.clone();
	    ArrayList<Point> points2 = (ArrayList<Point>) points.clone(); // prefer not to modify points
	    
	    while (!Evaluation.isValide(rest, fvs)) {
	    		Point p_max = null;
	    		int c_min = Integer.MAX_VALUE; // min cycleness, what we try to minimize
	    
	    		for (Point p : rest) {
	    			points2.remove(p);
	    			int c = cycleness(points2);
	    			points2.add(p);
	    			
	    			if (c < c_min) {
	    				c_min = c;
	    				p_max = p;
	    			}
	    		}
	    		
	    		rest.remove(p_max);
	    		fvs.add(p_max);
	    }
	    
	    return fvs;
	}
	
	public ArrayList<Point> doLocalSearch(ArrayList<Point> points, ArrayList<Point> fvs) {
		ArrayList<Point> newFvs = (ArrayList<Point>) fvs.clone();
		
		int i=0;
	    for (Point p : fvs) {
	    		System.out.println(i++ + "of "+ fvs.size());
	    		for (Point q : fvs) {
	    			if (p == q) {
	    				continue;
	    			}
	    			newFvs.remove(p);
	    			newFvs.remove(q);
	    			int pos = newFvs.size();
	    			for (Point o : points) {
	    				newFvs.add(pos, o);
	    				if (Evaluation.isValide(points, newFvs)) {
		    				return newFvs;
		    			}
	    				newFvs.remove(pos);
	    			}
	    			
	    			newFvs.add(p);
	    			newFvs.add(q);
	    		}
	    }
	    
	    return fvs;
	}
	
	public ArrayList<Point> localSearch(ArrayList<Point> points, ArrayList<Point> fvs) {
		ArrayList<Point> newFvs = fvs;
		
		do {
			fvs = newFvs;
			newFvs = doLocalSearch(points, fvs);
			System.out.println("Improved solution found");
		} while (newFvs.size() < fvs.size());
	    
	    return newFvs;
	}

  public ArrayList<Point> calculFVS(ArrayList<Point> points) {
    return localSearch(points, greedyFVS(points));
//	  return greedyByCycleness(points);
  }
}
