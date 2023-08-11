from flask import Flask, request

from chart_process import charts_process
from common_api import ques2answer
from report_process import gen_report

app = Flask(__name__)


@app.route('/getReport', methods=['GET'])
def get_report():
    message = request.args.get('message')

    new_report = gen_report(message)
    return new_report


@app.route('/getAnswer', methods=['GET'])
def generate_answer():
    question = request.args.get('question')

    answer = ques2answer(question)
    return answer


@app.route('/getOption', methods=['POST'])
def generate_charts():
    # 从request中获取表单请求的参数信息
    request_json = request.get_json()
    goal = request_json.get('goal')
    title = request_json.get('title')
    type = request_json.get('type')

    response = charts_process(goal, type, title)
    return response


if __name__ == '__main__':
    app.run()
