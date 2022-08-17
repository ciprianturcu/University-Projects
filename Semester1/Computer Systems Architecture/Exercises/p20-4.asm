bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
     a db 10
     b db 10
     c db 10
     d dw 16132

; our code starts here
segment code use32 class=code
    start:
    ;arithmetic expression to be calculated: (50-b-c)*2+a*a+d
    
        mov al,50
        mov ah,[b]
        sub al,ah
        mov ah,[c]
        sub al,ah
        ;calculated the (50-b-c) part in al
        
        mov bl,2
        mul bl
        mov bx,ax
        ;calculated the (50-b-c)*2 part in bx
        
        mov al,[a]
        mov cl,[a]
        mul cl
        ;calculated the a*a part, with result in ax
        
        add ax,bx
        ;calculated the (50-b-c)*2+a*a part, with result in ax
        
        mov cx,[d]
        add ax,cx
        ;calculates (50-b-c)*2+a*a+d and stores result in ax
        
    
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
