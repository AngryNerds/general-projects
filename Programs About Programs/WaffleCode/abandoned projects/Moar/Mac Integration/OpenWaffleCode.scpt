FasdUAS 1.101.10   ��   ��    k             l     ����  r       	  I    �� 
 
�� .sysodlogaskr        TEXT 
 m        �     O p e n   W a f f l e C o d e ?  ��  
�� 
btns  J           m       �    N o   ��  m       �    Y e s��    ��  
�� 
dflt  m       �    Y e s  ��  
�� 
cbtn  m   	 
   �    N o  ��  ��
�� 
appr   m     ! ! � " "  W a f f l e C o d e��   	 o      ���� 0 question  ��  ��     # $ # l    %���� % r     & ' & n     ( ) ( 1    ��
�� 
bhit ) o    ���� 0 question   ' o      ���� 	0 input  ��  ��   $  * + * l     ��������  ��  ��   +  , - , l   ; .���� . Z    ; / 0���� / =    1 2 1 o    ���� 	0 input   2 m     3 3 � 4 4  Y e s 0 k    7 5 5  6 7 6 I   &�� 8��
�� .sysodisAaleR        TEXT 8 m    " 9 9 � : :  I   s h a l l   o p e n   i t��   7  ; < ; l  ' '�� = >��   =  Tell waffle to open stuff    > � ? ? 2 T e l l   w a f f l e   t o   o p e n   s t u f f <  @ A @ l  ' '��������  ��  ��   A  B C B O   ' 5 D E D l  - 4 F G H F k   - 4 I I  J K J I  - 2������
�� .aevtodocnull  �    alis��  ��   K  L M L l  3 3�� N O��   N  or, activate?    O � P P  o r ,   a c t i v a t e ? M  Q R Q l  3 3�� S T��   S  open this_folder    T � U U   o p e n   t h i s _ f o l d e r R  V�� V l  3 3�� W X��   W  maybe use list folder?    X � Y Y , m a y b e   u s e   l i s t   f o l d e r ?��   G > 8We must make sure the app's name is set to "WaffleCode".    H � Z Z p W e   m u s t   m a k e   s u r e   t h e   a p p ' s   n a m e   i s   s e t   t o   " W a f f l e C o d e " . E m   ' * [ [ � \ \  W a f f l e C o d e C  ]�� ] l  6 6��������  ��  ��  ��  ��  ��  ��  ��   -  ^ _ ^ l     ��������  ��  ��   _  `�� ` l      �� a b��   aKE
useful code for later:
on opening folder this_folder	tell application "Microsoft Word"		activate	end tellend opening folder

also, to attach action to folder,
tell application "System Events"
	attach action to newFolder using ":Library:Scripts:Folder Action Scripts:OpenWaffle.scpt"
	#or can we use a rel path?
end tell
    b � c c� 
 u s e f u l   c o d e   f o r   l a t e r : 
 o n   o p e n i n g   f o l d e r   t h i s _ f o l d e r  	 t e l l   a p p l i c a t i o n   " M i c r o s o f t   W o r d "  	 	 a c t i v a t e  	 e n d   t e l l  e n d   o p e n i n g   f o l d e r 
 
 a l s o ,   t o   a t t a c h   a c t i o n   t o   f o l d e r , 
 t e l l   a p p l i c a t i o n   " S y s t e m   E v e n t s " 
 	 a t t a c h   a c t i o n   t o   n e w F o l d e r   u s i n g   " : L i b r a r y : S c r i p t s : F o l d e r   A c t i o n   S c r i p t s : O p e n W a f f l e . s c p t " 
 	 # o r   c a n   w e   u s e   a   r e l   p a t h ? 
 e n d   t e l l 
��       �� d e��   d ��
�� .aevtoappnull  �   � **** e �� f���� g h��
�� .aevtoappnull  �   � **** f k     ; i i   j j  # k k  ,����  ��  ��   g   h  ��  �� �� �� !���������� 3 9�� [��
�� 
btns
�� 
dflt
�� 
cbtn
�� 
appr�� 
�� .sysodlogaskr        TEXT�� 0 question  
�� 
bhit�� 	0 input  
�� .sysodisAaleR        TEXT
�� .aevtodocnull  �    alis�� <����lv������� E�O��,E�O��  a j Oa  	*j OPUOPY h ascr  ��ޭ