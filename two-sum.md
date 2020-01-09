## Two-Sum

Given an array of integers, return indices of the two numbers such that they add up to a specific target.

You may assume that each input would have exactly one solution, and you may not use the same element twice.

### Thinking:
While traversing the array, for nums[i], we want to find the complement of nums[i] to make target. 
Then if we had a O(1) lookup of its complement, we can increase our speed. Using HashMap solves this by the cost of space.
When traversing the array, check if it's complement is in the hashmap. If not, add the current number & index in. 

#### JAVA Implementation
```JAVA

class Solution {
  public int[] twoSum(int[] nums, int target) {
  Map<Integer, Integer> map = new HashMap<>();
  for (int i = 0; i < nums.length; i++) {
    int compl = target - nums[i];
    if (map.containsKey( compl )) {
      return new int { map.get(compl), i };
    }
    map.put( nums[i], i );
  }
  throw new IllegalArgumentException("No Two Sum Solution");
  }
}
```
    
#### Python Implementation
```Python
class Solution(object):
    def twoSum(self, nums, target):
        if len(nums) <= 1:
            return False
        buff_dict = {}
        for i in range(len(nums)):
            if nums[i] in buff_dict:
                return [buff_dict[nums[i]], i]
            else:
                buff_dict[target - nums[i]] = i
                
                
