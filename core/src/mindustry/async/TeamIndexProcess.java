package mindustry.async;

import arc.math.geom.*;
import mindustry.*;
import mindustry.game.*;
import mindustry.gen.*;

import java.util.*;

/** Creates quadtrees per unit team. */
public class TeamIndexProcess implements AsyncProcess{
    private QuadTree<Unit>[] trees = new QuadTree[Team.all.length];
    private int[] counts = new int[Team.all.length];

    public QuadTree<Unit> tree(Team team){
        if(trees[team.id] == null) trees[team.id] = new QuadTree<>(Vars.world.getQuadBounds(new Rect()));

        return trees[team.id];
    }

    public int count(Team team){
        return counts[team.id];
    }

    public void updateCount(Team team, int amount){
        counts[team.id] += amount;
    }

    @Override
    public void reset(){
        counts = new int[Team.all.length];
        trees = new QuadTree[Team.all.length];
    }

    @Override
    public void begin(){

        for(Team team : Team.all){
            if(trees[team.id] != null){
                trees[team.id].clear();
            }
        }

        Arrays.fill(counts, 0);

        for(Unit unit : Groups.unit){
            tree(unit.team).insert(unit);
            counts[unit.team.id] ++;
        }
    }

    @Override
    public boolean shouldProcess(){
        return false;
    }
}
