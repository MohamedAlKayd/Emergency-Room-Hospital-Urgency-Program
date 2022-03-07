// Mohamed Yasser Anwar Mahmoud AlKayd
// Emergency Room Hospital Urgency Priority Queue Program 

// - Start of the Program -

import java.util.ArrayList;
import java.util.HashMap;
public class ERPriorityQueue{

	public ArrayList<Patient>  patients;
	public HashMap<String,Integer>  nameToIndex;
	public ERPriorityQueue(){
		patients = new ArrayList<Patient>();
		patients.add( new Patient("dummy", 0.0) );
		nameToIndex  = new HashMap<String,Integer>();
	}

	private int parent(int i){
		return i/2;
	}

	private int leftChild(int i){
		return 2*i;
	}

	private int rightChild(int i){
		return 2*i+1;
	}

	public void upHeap(int i){
		while((i>1) && (patients.get(i).priority < patients.get(i/2).priority)) {
			swap(i, i/2);
			i=i/2;
		}
	}

	public void swap(int x, int y){
		Patient temporary1 = patients.get(x);
		Patient temporary2 = patients.get(y);
		patients.set(x,temporary2);
		patients.set(y,temporary1);
		nameToIndex.put(temporary1.getName(), y);
		nameToIndex.put(temporary2.getName(), x);
	}

	public void downHeap(int i){
		while(2*i <= patients.size()-1){
			int child = 2*i;
			if(child < patients.size()-1) {
				if (patients.get(child + 1).priority < patients.get(child).priority) {
					child = child + 1;
				}
			}
			if(patients.get(child).priority < patients.get(i).priority){
				swap(i, child);
				i = child;
			}
			else {
				return ;
			}
		}
	}

	public boolean contains(String name){
		return nameToIndex.containsKey(name);
	}

	public double getPriority(String name){
		if(!(nameToIndex.containsKey(name))){
			return -1;
		} else{
			int index = nameToIndex.get(name);
			return patients.get(index).priority;
		}
	}

	public double getMinPriority() {
		if (patients == null) {
			return -1;
		} else {
			if(patients.size() > 1){
				double minimumPriority = patients.get(1).getPriority();
				return minimumPriority;
			}else{
				return -1;
			}
		}
	}

	public String removeMin() {
		if ((patients == null) || (patients.size() <= 1)) {
			return null;
		}
		else {
			String temp = patients.get(1).name;
			remove(patients.get(1).name);
			return temp;
		}
	}

	public String peekMin(){
		if((patients == null)||(patients.size() <= 1)){
			return null;
		}else{
			return patients.get(1).name;
		}
	}

	public boolean  add(String name, double priority){
		if(nameToIndex.containsKey(name)){
			return false;
		}else{
			Patient newPatient = new Patient(name, priority);
			patients.add(newPatient);
			nameToIndex.put(name, patients.size()-1);
			this.upHeap(patients.size()-1);
			return true;
		}
	}

	public boolean  add(String name){
		if(nameToIndex.containsKey(name)) {
			return false;
		}else {
			double infinity = 1.0/0.0;
			Patient newPatient2 = new Patient(name, infinity);
			patients.add(newPatient2);
			nameToIndex.put(name, patients.size()-1);
			this.upHeap(patients.size()-1);
			return true;
		}
	}
	
	public boolean remove(String name) {
		if(!(contains(name))) {
			return false;
		}else{
			int indexnumber = nameToIndex.get(name);
			swap(indexnumber, patients.size()-1);
			patients.remove(patients.size()-1);
			nameToIndex.remove(name);
			if((indexnumber>1)&&(patients.size()>indexnumber)){
				if(patients.get(indexnumber).priority < patients.get(indexnumber/2).priority){
					upHeap(indexnumber);
					return true;
				}
			}
			downHeap(indexnumber);
			return true;
		}
	}

	/*
	 *   If new priority is different from the current priority then change the priority
	 *   (and possibly modify the heap).
	 *   If the name is not there, return false
	 */

	public boolean changePriority(String name, double priority) {
		if (!(contains(name))) {
			return false;
		}
		else {
			int indexnumber1 = nameToIndex.get(name);
			patients.get(indexnumber1).setPriority(priority);
			upHeap(indexnumber1);
			downHeap(indexnumber1);
			return true;
		}
	}

	public ArrayList<Patient> removeUrgentPatients(double threshold){

		ArrayList<Patient> urgentPatients = new ArrayList<Patient>();
		ERPriorityQueue newQueue = new ERPriorityQueue();
		int j = 0;
		for(int i=1; i<patients.size();i++){
			if(patients.get(i).priority <= threshold){
				urgentPatients.add(patients.get(i));
			}else{
				j++;
				newQueue.patients.add(patients.get(i));
				newQueue.nameToIndex.put(patients.get(i).getName(), j);
			}
		}
		patients = newQueue.patients;
		nameToIndex = newQueue.nameToIndex;
		return urgentPatients;
	}

	public ArrayList<Patient> removeNonUrgentPatients(double threshold){
		ArrayList<Patient> urgentPatients = new ArrayList<Patient>();
		ERPriorityQueue newQueue = new ERPriorityQueue();
		int j = 0;
		for(int i=1; i<patients.size();i++){
			if(patients.get(i).priority >= threshold){
				urgentPatients.add(patients.get(i));
			}else{
				j++;
				newQueue.patients.add(patients.get(i));
				newQueue.nameToIndex.put(patients.get(i).getName(), j);
			}
		}
		patients = newQueue.patients;
		nameToIndex = newQueue.nameToIndex;
		return urgentPatients;
	}

	static class Patient{
		private String name;
		private double priority;
		Patient(String name,  double priority){
			this.name = name;
			this.priority = priority;
		}
		Patient(Patient otherPatient){
			this.name = otherPatient.name;
			this.priority = otherPatient.priority;
		}
		double getPriority() {
			return this.priority;
		}
		void setPriority(double priority) {
			this.priority = priority;
		}
		String getName() {
			return this.name;
		}
		@Override
		public String toString(){
			return this.name + " - " + this.priority;
		}
		public boolean equals(Object obj){
			if (!(obj instanceof  ERPriorityQueue.Patient)) return false;
			Patient otherPatient = (Patient) obj;
			return this.name.equals(otherPatient.name) && this.priority == otherPatient.priority;
		}
	}
}

// - End of the Program -