; f(E L StackNodes Stack Counts) - nil n= 0
;                                - e U StackNodes, if l1 = E
;                                - decrease parent, if l1.child_count = 0
;                                - f (E (l2..ln) (l1.node U StackNodes) (l1.child_count U StackCounts))

; decrease parent 

(defun f (E L Nodes Counts)
    (cond
        ((null L) nil)  ; lista nula returnam null
        ((eq (car L) E) (cons E Nodes)) ; am gasit nod cautat atasam la path si returnam 
        ((eq (cadr L) 0) ; daca am ajuns la frunza
            (cond ;decrease parent
                ((eq (car Counts) 2) (f E (cons (car Nodes) (cons (- (car Counts) 1) (cddr L))) (cdr Nodes) (cdr Counts))) ; pe stiva avem primu element cu 2 copii il reintorducem in lista cu copil minus 1 
                ((null Nodes) (f E (cddr L) Nodes Counts)) ;daca nu mai este nimic in stiva mergem mai departe inseamna ca am tratat pathurile pe care nu exista elem
                (T (f E L (cdr Nodes) (cdr Counts))) ; pop de pe stiva
            )
        )
        (T (f E (cddr L) (cons (car L) Nodes) (cons (cadr L) Counts))) ; daca am ajuns la nod cu copii reapelam functia cu noul nod pushat pe stack
    )
)

(defun path (E L)
    (cond
        (T (reverse (f E L () ())))
    )
)

(print (path 'E '(A 2 B 0 C 2 D 0 E 0)))
