package com.cooksys.ftd.assignments.collections;

import com.cooksys.ftd.assignments.collections.hierarchy.Hierarchy;
import com.cooksys.ftd.assignments.collections.model.Capitalist;
import com.cooksys.ftd.assignments.collections.model.FatCat;
import com.cooksys.ftd.assignments.collections.model.WageSlave;

import java.util.*;

public class MegaCorp implements Hierarchy<Capitalist, FatCat> {
	Map<FatCat, Set<Capitalist>> corpMap = new HashMap<>();
	//Set<Map.Entry> capitalistSet = new HashSet<>();

    /**
     * Adds a given element to the hierarchy.
     * <p>
     * If the given element is already present in the hierarchy,
     * do not add it and return false
     * <p>
     * If the given element has a parent and the parent is not part of the hierarchy,
     * add the parent and then add the given element                   
     * <p>
     * If the given element has no parent but is a Parent itself, 
     * add it to the hierarchy
     * <p>
     * If the given element has no parent and is not a Parent itself,
     * do not add it and return false
     *
     * @param capitalist the element to add to the hierarchy
     * @return true if the element was added successfully, false otherwise
     */
    @Override
    public boolean add(Capitalist capitalist) {

    	// 1
    	if (corpMap.containsKey(capitalist) || corpMap.entrySet().contains(capitalist) || !capitalist.hasParent() && !(capitalist instanceof FatCat) || capitalist == null){
    		return false;
    	}
    	
    	if (capitalist instanceof WageSlave) {
    		
    	}
    	
    	FatCat parent = capitalist.getParent();
    	Capitalist child = capitalist;
    	
    	while (parent != null) {
    		if (!corpMap.containsKey(parent)) {
    			corpMap.put(parent, new HashSet<>());
    		}
    		corpMap.get(parent).add(child);
    		child = parent;
    		parent = parent.getParent();
    	}
    	
    	if(capitalist instanceof FatCat) {
    		corpMap.put((FatCat)capitalist, new HashSet<>());
    	}
    	return true;

    }

    /**
     * @param capitalist the element to search for
     * @return true if the element has been added to the hierarchy, false otherwise
     */
    @Override
    public boolean has(Capitalist capitalist) {		 
		 // if FatCat
		 if (corpMap.containsKey(capitalist) || corpMap.get(capitalist.getParent()).contains(capitalist)) {
			 return true;
		 }
		 return false;
    }

    /**
     * @return all elements in the hierarchy,
     * or an empty set if no elements have been added to the hierarchy
     */
    @Override
    public Set<Capitalist> getElements() {
    	if (corpMap.isEmpty()) {
    		return new HashSet<>();
    	}
    	
    	Set<Capitalist> allElements = new HashSet<>();
    	
    	allElements.addAll(this.getParents());
    	for (FatCat parent : corpMap.keySet()) {
    		allElements.addAll(corpMap.get(parent));
    	}

    	//<Capitalist> a = allElements.iterator();
//    	while (a.hasNext()) {
//        	allElements.addAll(a.next().getChildren());
//    	}
    	
    	
//    	Set<Capitalist> allElements = new HashSet<>();
//    	Iterator<Map.Entry<FatCat, Set<Capitalist>>> iterator = corpMap.entrySet().iterator();
//    	while(iterator.hasNext()) {
//    		Map.Entry mapEntry = (Map.Entry)iterator.next();
//    		allElements.add((Capitalist) mapEntry.getValue());
//    	}
    	
    	return allElements;
    }

    /**
     * @return all parent elements in the hierarchy,
     * or an empty set if no parents have been added to the hierarchy
     */
    @Override
    public Set<FatCat> getParents() {
    	if (corpMap.isEmpty()) {
    		return new HashSet<>();
    	}
    	
    	Set<FatCat> parentSet = new HashSet<>();
    	parentSet = corpMap.keySet();
    	return parentSet;
    }

    /**
     * @param fatCat the parent whose children need to be returned
     * @return all elements in the hierarchy that have the given parent as a direct parent,
     * or an empty set if the parent is not present in the hierarchy or if there are no children
     * for the given parent
     */
    @Override
    public Set<Capitalist> getChildren(FatCat fatCat) {
    	if (!corpMap.containsKey(fatCat)|| corpMap.get(fatCat) == null) {
    		return new HashSet<>();
    	}
    	Set<Capitalist> defensiveChildren = new HashSet<>();
    	//defensiveChildren.addAll(corpMap.get(fatCat));
    	
    	for (Capitalist f : corpMap.get(fatCat)) {
    		defensiveChildren.add(f);
    	}
    	return defensiveChildren;
    }

    /**
     * @return a map in which the keys represent the parent elements in the hierarchy,
     * and the each value is a set of the direct children of the associate parent, or an
     * empty map if the hierarchy is empty.
     */
    @Override
    public Map<FatCat, Set<Capitalist>> getHierarchy() {
    	return corpMap;
    }

    /**
     * @param capitalist
     * @return the parent chain of the given element, starting with its direct parent,
     * then its parent's parent, etc, or an empty list if the given element has no parent
     * or if its parent is not in the hierarchy
     */
    @Override
    public List<FatCat> getParentChain(Capitalist capitalist) {

    	List<FatCat> parentChain = new ArrayList<>();
    	if (!capitalist.hasParent() || !corpMap.containsKey(capitalist.getParent())) {
    		return new ArrayList<>();
    	}

    	else {
        	if (capitalist instanceof FatCat) {
        		parentChain.add((FatCat) capitalist);
        	}
    		FatCat parent = capitalist.getParent();
    		while (parent != null) {
    			parentChain.add(parent);
    			parent = parent.getParent();
    		}
    	}
    	return parentChain;
    }
}
