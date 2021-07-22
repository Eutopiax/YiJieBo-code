import cv2

def recordCamera(path, type):
    # 调用摄像头
    videoCapture = cv2.VideoCapture(path)

    # 设置帧率
    fps = 30

    # 获取窗口大小
    size = (int(videoCapture.get(cv2.CAP_PROP_FRAME_WIDTH)), int(videoCapture.get(cv2.CAP_PROP_FRAME_HEIGHT)))
    filename = 'SaveVideo'+type+'.avi'
    print(filename)
    # 调用VideoWrite（）函数
    videoWrite = cv2.VideoWriter(filename, cv2.VideoWriter_fourcc('I', '4', '2', '0'), fps, size)

    # 先获取一帧，用来判断是否成功调用摄像头
    success, frame = videoCapture.read()

    # 通过设置帧数来设置时间,减一是因为上面已经获取过一帧了
    numFrameRemainling = fps * 5 - 1

    # 通过循环保存帧
    while success and numFrameRemainling > 0:
        videoWrite.write(frame)
        success, frame = videoCapture.read()
        numFrameRemainling -= 1

    # 释放摄像头
    videoCapture.release()
