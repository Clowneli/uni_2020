public class InOrder {
    //Don't change this
    public boolean OutOfOrder(int n1, int n2, int n3) {
	return (n1 > n2) || (n2 > n3);
    }

    //The original and messy InOrder, leave this as an example of what not to do
    public boolean InOrder(int n1, int n2, int n3) {
    if ((n2 > n1) && (n3 > n2)) {
        return true;
    } else if ((n2 == n1) && (n3 == n1)) {
        return true;
    }
    return false;
  }
    
    //The new and improved InOrder for part 5, call OutOfOrder
    public boolean inOrder5a(int n1, int n2, int n3) {
	if(!(OutOfOrder(n1,n2,n3))){return true;}
	return false;
    }

    //The newer and improveder InOrder for part 6, inline
    public boolean inOrder5b(int n1, int n2, int n3) {
	if(InOrder(n1,n2,n3)){return true;}
	return false;
    }
    
    //Change to make more readable (this is debatable!) by calling InOrder 
    public boolean InBetween(int m, int p, int q) {
	if (InOrder(m,p,q) || InOrder(q,p,m)){return true;}
	return false;
    }
}