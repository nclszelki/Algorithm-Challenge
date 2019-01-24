// Find duplicate in an array


// analysis : O(n^2)
// brutal force
private static void brutalforce(int[] input){
  for (int i = 0; i < input.length; i++){
    for (int y = 0; y < input.length; y++){
      if (input[i] == input[y]){
        System.out.println("Find duplicate" + input[i]);
      } else {
        continue;
      }
    }
  }
}


// using sort
// Analysis:  O(nlogn)
private static void sorting(int [] input){
  Arrays.sort(input);
  for (int i = 0; i < input.length; i++){
    if (input[i] == input[i+1]){

      System.out.println("Find duplicate" + input[i]);
    }
  }

}


// using hashset : hashset can only store unique items
// Anlysis : O(n) and O(n) space complexity


private static void hashsetway(int [] input){
  hashset<integer> set = new hashset<integer>();

  for (int element : input){
    if (!set.add(element)){
      System.out.println("Find duplicate" + element);
    }
  }
}
