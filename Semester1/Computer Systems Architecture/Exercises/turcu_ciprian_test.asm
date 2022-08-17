bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,fscanf,fprintf,fopen,printf             ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fscanf msvcrt.dll
import fprintf msvcrt.dll
import fopen msvcrt.dll
import printf msvcrt.dll

                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    file_name db "origin.txt", 0
    destination_file db "destination.txt",0
    file_descriptor_origin db -1, 0
    file_descriptor_destination db -1,0
    len equ 100
    proposition times 100 db 0
    access_read_mode db "%r", 0
    access_write_mode db "%w",0
    dot db ".",0
    read_string_format db "%s",0
    print_string_format db "%s",0
    
    
    ok db 0
    
    

; our code starts here
segment code use32 class=code
    start:
        push dword access_read_mode
        push dword file_name
        call [fopen]
        add esp, 4*2
        
        mov [file_descriptor_origin],eax
        
        cmp eax, 0
        
        mov edi,1
            
        push dword access_write_mode
        push dword destination_file
        call [fopen]
        add esp, 4*2
            
        mov [file_descriptor_destination],eax
        cmp eax,0
            
            
            my_loop:
            
            push dword proposition 
            push dword read_string_format
            push dword [file_descriptor_origin]
            call [fscanf]
            add esp,4*3
            
            cmp eax,-1
            je exit_loop
            
            mov eax,0
            mov eax,[edi]
            mov edx,0
            mov ebx,2
            div ebx
            
            inc edi
            
            cmp edx, 0
            jne my_loop
            
            
            push dword proposition
            push dword print_string_format
            push dword [file_descriptor_destination]
            call [fprintf]
            add esp, 4*3
            
            jmp my_loop
            
        exit_loop:
        
        final:
        
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
