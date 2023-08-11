from langchain import SQLDatabase, PromptTemplate
from langchain.chains import LLMChain, SQLDatabaseSequentialChain
from langchain.chains import SequentialChain
from langchain.chat_models import ChatOpenAI
from langchain.prompts import ChatPromptTemplate

from config import API_KEY, DB_URI

llm = ChatOpenAI(openai_api_key=API_KEY, temperature=0)

# prompt template 1
first_prompt = ChatPromptTemplate.from_template(
    "请针对```分隔的内容进行提问。要求拆分问题时，保留上下文的条件关系。"
    "EXAMPLE: ‘一共有4个订单，其中3个订单的创建时间在今天之前。物品总价为200美元，来自2个不同的消费者’"
    "应转换为‘有多少个订单？其中有几个是在今天之前创建的？物品的总价是多少？来自多少个不同的消费者？’"
    "ATTENTION: 你只需要识别句子中的信息并给出问题，不需要不存在的信息。"
    "```{template}```"
)

# Chain 1
chain_one = LLMChain(llm=llm, prompt=first_prompt, output_key="query")

# prompt template 2
_DEFAULT_TEMPLATE = """
Given input questions, first create syntactically correct sql queries to run for each question,
then concatenate the results of the query and return the answer.
ATTENTION: notice the columns of the same name in different tables. Avoid ambiguous name in the query.
Use the following format:
    Question: "Question here"
    SQLQuery: "SQL Query to run"
    SQLResult: "Result of the SQLQuery"
    Answer: "Final answer here"
"""
second_prompt = PromptTemplate(
    input_variables=[], template=_DEFAULT_TEMPLATE
)

db = SQLDatabase.from_uri(DB_URI)

# chain 2
chain_two = SQLDatabaseSequentialChain.from_llm(llm, db, verbose=True, decider_prompt=second_prompt,
                                                output_key="data")

# prompt template 3
third_prompt = ChatPromptTemplate.from_template(
    "这是一段模板```{template}```，这是需要展示的数据描述```{data}```。请用此数据替换模板中的对应数据。"
    "EXAMPLE: 若模板为'共有4个订单，其中3个订单的创建时间在今天之前，物品总价格为200美元',数据为'有3个订单，1个订单在今天之前创建，他们的价格为100美元'时，"
    "应当给出的回答是'共有3个订单，其中2个订单的创建时间在今天之前，物品总价格为100美元'."
    "ATTENTION: 在回答中，忽略模板中存在的但在数据中找不到的内容"
)

# chain 3
chain_three = LLMChain(llm=llm, prompt=third_prompt,
                       output_key="result")

#  链起来
overall_chain = SequentialChain(
    chains=[chain_one, chain_two, chain_three],
    input_variables=["template"],
    output_variables=["query", "data", "result"],
    verbose=True
)

if __name__ == '__main__':
    result = overall_chain(
        "有5个订单在2020年4月1日之前")
    print(result)
