class Solution 
{ 
    private static class UF
    {
        int[] parent;
        byte[] rank;
        int n;
        UF(int n)
        {
            this.n = n;
            parent = new int[n];
            rank = new byte[n];
            for(int i = 0; i < n; i++)
            {
                parent[i] = i;
            }
        }
        private int find(int i)
        {
            while(i != parent[i])
            {
                parent[i] = parent[parent[i]];
                i = parent[i];
            }
            return i;
        }
        
        public boolean connected(int a, int b)
        {
            return find(a) == find(b);
        }
        
        public void union(int a, int b)
        {
            int pa = find(a);
            int pb = find(b);
            
            if(pa == pb)    return;
            
            if(rank[pa] > rank[pb]) parent[pb] = pa;
            if(rank[pa] < rank[pb]) parent[pa] = pb;
            else
            {
                parent[pb] = pa;
                rank[pa] ++;
            }
        }
    }
    
    public boolean gcdSort(int[] a) 
    {
        int n = a.length;
        Set<Integer> set = new HashSet<>();
        for(int i : a)  set.add(i);
        
        int max_num = Integer.MIN_VALUE;
        for(int i : a)  max_num = Math.max(max_num, i);
        
        UF uf = new UF(max_num + 1);
        
        int[] sorted_arr = new int[n];
        
        for(int i = 0; i < n; i++)
            sorted_arr[i] = a[i];
        
        Arrays.sort(sorted_arr);
        
        //sieve
        boolean[] sieve = new boolean[max_num + 1];
        for(int i = 2; i * 2 <= max_num; i++)
        {
            if(sieve[i])    continue;
            for(int j = 2; i * j <= max_num; j ++)
            {
				// i is a prime number
				// i * j is connected to i (of course if i * j is there in arr)
                if(set.contains(i * j)) uf.union(i, i * j);
                sieve[i * j] = true;
            }
        }
        
		//checking if arr[i] and sorted[i] is in the same group
        for(int i = 0; i < n; i++)
        {
            if(!uf.connected(a[i], sorted_arr[i])) return false;
        }
        
        return true;
    }
}
