;first make sure the number of arguments passed into the scripts is more than 1
If $CmdLine[0]<2 Then
   Exit MsgBox(48, "����", "2��������")
EndIf
   ;ifparmasnum<2,thenbreak
   ;CmdLine[0]
   ;����������
;CmdLine[1]
;��һ������(�ű����ƺ���)
;CmdLine[2]
;�ڶ�������
;���Ǵ�cmd�������
handleUpload($CmdLine[1],$CmdLine[2])

;�����ϴ���������������������һ������������֣��ڶ��������ļ�·��
Func handleUpload($browser,$uploadfile)
Dim $title
;����һ��title����
;���ݵ�����title���ж���ʲô�����
If $browser="ie" Then; ����IE�����
$title="ѡ��Ҫ���ص��ļ�"
ElseIf $browser="chrome" Then
   ;����ȸ������
$title="��"
ElseIf $browser="firefox" Then
; �����������
$title="�ļ��ϴ�"
EndIf

if WinWait($title,"",5) Then
   ;�ȴ��������֣����ȴ�ʱ����4��
               WinActivate($title)
			   ;�ҵ���������֮�󣬼��ǰ����
               ControlSetText($title,"","Edit1",$uploadfile)
			   ;���ļ�·����������򣬴�"Edit1"����FinderTool��ȡ����
               ControlClick($title,"","Button1")
			   ;���������ߴ򿪻����ϴ���ť����"Button1"ʹ��FinderTool��ȡ����
        Else
        Return False
	 EndIf
EndFunc