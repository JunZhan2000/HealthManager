from QASystem.chatbot_graph import ChatBotGraph

class Prediction(object):
    def __init__(self):
        self.hander = ChatBotGraph()

    def predict_(self, department, title, ask):
        return(self.hander.chat_main(ask))

Pre = Prediction()

def predict(department, title, ask):
    return(Pre.predict_(department, title, ask))
    
if __name__ == "__main__":
    pre = Prediction()
    while(True):
        s = input('请输入:')
        print('回答:'+pre.predict(None, None, s))