package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
	

  public ArrayList<Point> calculFVS(ArrayList<Point> points) {
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
}
