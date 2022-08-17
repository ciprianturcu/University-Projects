bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
       a db 2, 1, 3, 3, 4, 2, 6
       b db 4, 5, 7, 6, 2, 1
       la equ b-a
       lb equ $-b
       lr equ la+lb
       r times lr db 0 
; our code starts here
segment code use32 class=code
    start:
        mov ecx, lb ; we put the length lb in ecx in order to make the loop
        mov esi, 0  ; we put esi on 0 so that we can put in the destination string r the elems from b
        mov edi, lb-1 ; index to go in reverse order on the elements of b
        jecxz Sfarsit_b
        Repeta_b:
                mov al, [b+edi]
                mov [r+esi], al
                inc esi
                dec edi
        loop Repeta_b
        ; loop to put in r the elements of b in reverse order
        Sfarsit_b:
        mov ecx, la  ; we put the length la in ecx in order to make the loop
        mov esi, lb  ; we put lb in esi so that we have the starting point in r after the elements from string b
        mov edi, 0   ; index to go through the elements of a
        jecxz Sfarsit_a
        Repeta_a:
                 mov bl, [a+edi]
                 test bl,1 ; testing if the element is even or not
                 jnz conditie ;jumps at ZF=0, which also means that the element is not even
                 mov [r+esi],bl
                 inc esi
                 conditie:
                 inc edi
        loop Repeta_a
        ; loop to put in r the even elements of a
        Sfarsit_a:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
