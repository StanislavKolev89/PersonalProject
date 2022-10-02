const productId= document.getElementById('productId').value
const commentForm = document.getElementById('commentForm')
commentForm.addEventListener("submit", createComment)

const csrfHeaderName = document.head.querySelector('[name=_csrf_header]').content
const csrfHeaderValue = document.head.querySelector('[name=_csrf]').content

const commentsHolder = document.getElementById('commentBucket')

async function createComment(event) {
    event.preventDefault()

    const commentContent = document.getElementById('content').value

    fetch(`http://localhost:8080/api/${productId}/comments`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            [csrfHeaderName]: csrfHeaderValue
        },
        body: JSON.stringify({
            content: commentContent
        })
    }).then(res => res.json())
        .then(data => {
            document.getElementById('content').value = ""
            commentsHolder.innerHTML += injectComment(data)
            console.log(data);
        })
}

function injectComment(comment) {
    let commentHtml = '<div>\n'
    commentHtml += `<h4>${comment.commentCreator}</h4>\n`
    commentHtml += `<p>${comment.content}</p>\n`
    commentHtml += '</div>\n'

    return commentHtml
}

fetch(`http://localhost:8080/api/${productId}/comments`, {
    headers: {
        "Accept": "application/json"
    }
}).then(res => res.json())
    .then(data => {
        for(let comment of data) {
            commentsHolder.innerHTML += injectComment(comment)
        }
    })